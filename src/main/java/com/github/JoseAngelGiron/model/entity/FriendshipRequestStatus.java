package com.github.JoseAngelGiron.model.entity;

public enum FriendshipRequestStatus {
        PENDING("Pendiente"),
        ACCEPTED("Aceptada"),
        REJECTED("Rechazada");


        private final String displayText;

        FriendshipRequestStatus(String displayText) {
                this.displayText = displayText;
        }

        public String getDisplayText() {
                return displayText;
        }

}
