package multipart.service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class DownloadServiceImpl implements  DownloadService {

    private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

    private final String accessKey = "your access key";
    private final String secretKey = "your  secret key";

    @Override
    public void download() {
    Regions clientRegion = Regions.US_EAST_1;

    String bucketName = "your bucket name";
    String key = "data";
    String downloadFilePath = "/Users/guru/Downloads/MultipartUpload/src/test/download.csv";
    try {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withRegion(clientRegion)
                            .withCredentials(new ProfileCredentialsProvider())
                            .build();

                    // Get the object from the bucket
        S3Object s3Object = s3Client.getObject(bucketName, key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

                    // Create a new file and write the S3 object data to it
        FileOutputStream outputStream = new FileOutputStream(new File(downloadFilePath));
        byte[] readBuffer = new byte[1024];
                    int readLength;
                    while ((readLength = inputStream.read(readBuffer)) > 0) {
                        outputStream.write(readBuffer, 0, readLength);
                    }
                    inputStream.close();
                    outputStream.close();
                    System.out.println("File downloaded successfully to " + downloadFilePath);
                } catch (AmazonServiceException e) {
                    // The call was transmitted successfully, but Amazon S3 couldn't process
                    // it and returned an error response.
                    e.printStackTrace();
                } catch (SdkClientException e) {
                    // Amazon S3 couldn't be contacted for a response, or the client
                    // couldn't parse the response from Amazon S3.
                    e.printStackTrace();
                } catch (IOException e) {
                    // File IO error
                    e.printStackTrace();
                }

    }
}
