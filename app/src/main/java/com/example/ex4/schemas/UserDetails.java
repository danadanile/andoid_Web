package com.example.ex4.schemas;


public class UserDetails {
    private String username;
    private String displayName;
    private String profilePic;

    public UserDetails(String username, String displayName, String profilePic) {
        this.username = username;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
