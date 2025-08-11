import db.RecordRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Application {
    public static void main(String[] args) {
        RecordRepository repository = new RecordRepository();
        System.out.println(repository.getRecords());
    }
}
