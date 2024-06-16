package zaarour.dev.springai.imagegen;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zaarour.dev.springai.imagegen.ImageGenRequest;

@Controller
public class ImageGenController {

    private final ImageClient imageClient;

    public ImageGenController(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    // endpoint to generate an image
    @PostMapping("/imagegen")
    public String imageGen(@RequestBody ImageGenRequest request) {
        ImageOptions options = ImageOptionsBuilder.builder()
                .withModel("dall-e-3")
                .build();
        ImagePrompt imagePrompt = new ImagePrompt(request.prompt(), options);
        ImageResponse response = imageClient.call(imagePrompt);
        String imageUrl = response.getResult().getOutput().getUrl();

        return "redirect:" + imageUrl;
    }

}