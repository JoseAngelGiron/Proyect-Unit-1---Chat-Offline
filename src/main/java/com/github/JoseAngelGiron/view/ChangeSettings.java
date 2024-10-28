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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        setImages();
        setDefaultImage();

    }
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

    private List<File> loadImagesFromDirectory(File directory) {
        return List.of(Objects.requireNonNull(directory.listFiles(file -> {
            String lowercaseName = file.getName().toLowerCase();
            return lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".jpeg") ||
                    lowercaseName.endsWith(".png") || lowercaseName.endsWith(".gif");
        })));
    }

    private void setDefaultImage(){
        currentUser = UserSession.UserSession().getUserLoggedIn();
        String actualPhoto = currentUser.getPhoto();
        Image image = new Image(new File(actualPhoto).toURI().toString());
        bigImage.setImage(image);
    }

}
