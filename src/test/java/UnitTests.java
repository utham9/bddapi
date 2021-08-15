import com.example.qa.api.utils.FileOperations;
import org.testng.annotations.Test;

public class UnitTests {

  @Test
  public void directory() {
    String reportFolder = FileOperations.generateReportFolder();
    System.out.println(reportFolder);
  }
}
