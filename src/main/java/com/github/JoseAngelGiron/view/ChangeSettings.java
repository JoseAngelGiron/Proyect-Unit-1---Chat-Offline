package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.User;
import com.github.JoseAngelGiron.model.session.UserSession;
import com.github.JoseAngelGiron.model.xmlDataHandler.UserHandler;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChangeSettings extends Controller implements Initializable {
    @FXML
    private TableView<ImageView> tableOfImages;
    @FXML
    private TableColumn<ImageView, ImageView> imagesColumn;
    @FXML
    private ImageView bigImage;

    @FXML
    private TextField currentStatus;

    private User currentUser;

    private final static String imagesPath = "C:\\Users\\the_l\\IdeaProjects\\Project-1DA\\src\\main\\resources\\com\\github\\JoseAngelGiron\\view\\assets";

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }
    /**
     * Initializes the view, setting up images and default profile picture.
     *
     * This method retrieves the currently logged-in user and populates
     * the image selection table with available images from the specified directory.
     *
     * @param url             The location used to resolve relative paths for the root object,
     *                        or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if
     *                        the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        setImages();
        setDefaultImage();

    }

    /**
     * Changes the user's profile picture and status based on the current selections.
     *
     * This method updates the logged-in user's profile with the new photo URL and status.
     */
    @FXML
    public void changeSetting() {
        String url = bigImage.getImage().getUrl();
        String photoUrl = url.replace("file:/", "");
        User user = UserSession.UserSession().getUserLoggedIn();
        UserSession.UserSession().getUserLoggedIn().setPhoto(photoUrl);
        user.setPhoto(photoUrl);

        if(!currentStatus.getText().isEmpty()){
            user.setStatus(currentStatus.getText());
        }

        UserHandler.build().update(user);
    }

    /**
     * Sets the images in the TableView from the specified images directory.
     *
     * This method populates the table with images and sets up the click event
     * to display the selected image in a larger view.
     */
    private void setImages() {
        File directory = new File(imagesPath);
        List<File> imageFiles = loadImagesFromDirectory(directory);


        ObservableList<ImageView> imageViews = FXCollections.observableArrayList(
                imageFiles.stream()
                        .map(file -> {
                            ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            imageView.setPreserveRatio(true);
                            return imageView;
                        })
                        .collect(Collectors.toList())
        );


        tableOfImages.setItems(imageViews);
        imagesColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableOfImages.setOnMouseClicked(event -> {
            ImageView selectedImageView = tableOfImages.getSelectionModel().getSelectedItem();
            if (selectedImageView != null) {

                bigImage.setImage(selectedImageView.getImage());
            }
        });
    }

    /**
     * Loads image files from the specified directory.
     *
     * @param directory The directory to load images from.
     * @return A list of image files in the directory.
     */
    private List<File> loadImagesFromDirectory(File directory) {
        return List.of(Objects.requireNonNull(directory.listFiles(file -> {
            String lowercaseName = file.getName().toLowerCase();
            return lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".jpeg") ||
                    lowercaseName.endsWith(".png") || lowercaseName.endsWith(".gif");
        })));
    }

    /**
     * Sets the default image to the current user's profile picture.
     *
     * This method retrieves the user's photo path and displays it in the larger image view.
     */
    private void setDefaultImage(){
        currentUser = UserSession.UserSession().getUserLoggedIn();
        String actualPhoto = currentUser.getPhoto();
        Image image = new Image(new File(actualPhoto).toURI().toString());
        bigImage.setImage(image);
    }

}
