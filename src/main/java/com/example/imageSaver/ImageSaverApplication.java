package com.example.imageSaver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@SpringBootApplication
public class ImageSaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageSaverApplication.class, args);
	}

	@Bean
	public CommandLineRunner initBucket(
			S3Client s3Client,
			@Value("${minio.bucket-name}") String bucketName
	) {
		return args -> {
			try {
				s3Client.headBucket(
						HeadBucketRequest.builder()
								.bucket(bucketName)
								.build());
			} catch (S3Exception e) {
				if (e.statusCode() != 404) {
					throw e;
				}

				s3Client.createBucket(
						CreateBucketRequest.builder()
								.bucket(bucketName)
								.build());
			}

			String bucketPolicy = """
                {
                  "Version": "2012-10-17",
                  "Statement": [
                    {
                      "Effect": "Allow",
                      "Principal": "*",
                      "Action": ["s3:GetObject"],
                      "Resource": ["arn:aws:s3:::%s/*"]
                    }
                  ]
                }
                """.formatted(bucketName);

			s3Client.putBucketPolicy(
					PutBucketPolicyRequest.builder()
							.bucket(bucketName)
							.policy(bucketPolicy)
							.build());

		};
	}
}
