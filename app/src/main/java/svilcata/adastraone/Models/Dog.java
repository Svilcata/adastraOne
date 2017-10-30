package svilcata.adastraone.Models;

import java.util.List;

/**
 * Created by Svilcata on 30-Oct-17.
 */

public class Dog {
    private String mBreed;
    private List<String> mSubBreed;
    private String mRandomImage;

    public Dog(String breed, List<String> subBreed, String randomImage) {
        this.mBreed = breed;
        this.mSubBreed = subBreed;
        this.mRandomImage = randomImage;
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

    public String getmRandomImage() {
        return mRandomImage;
    }

    public void setmRandomImage(String mRandomImage) {
        this.mRandomImage = mRandomImage;
    }
}
