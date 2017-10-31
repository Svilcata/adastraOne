package svilcata.adastraone.Models;

import java.util.List;

public class Dog {
    private String mBreed;
    private List<String> mSubBreed;
    private String mRandomImageURL;

    public Dog(String breed, List<String> subBreed, String randomImage) {
        this.mBreed = breed;
        this.mSubBreed = subBreed;
        this.mRandomImageURL = randomImage;
    }

    public String getBreed() {
        return mBreed;
    }

    public void setBreed(String breed) {
        this.mBreed = breed;
    }

    public List<String> getSubBreed() {
        return mSubBreed;
    }

    public void setSubBreed(List<String> subBreed) {
        this.mSubBreed = subBreed;
    }

    public String getmRandomImageURL() {
        return mRandomImageURL;
    }

    public void setmRandomImageURL(String mRandomImageURL) {
        this.mRandomImageURL = mRandomImageURL;
    }
}
