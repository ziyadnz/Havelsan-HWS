import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ReadFileAndZip extends SimpleFileVisitor<Path> {

	private static ZipOutputStream zos;

	private Path sourceDir;

	public ReadFileAndZip(Path sourceDir) {
		this.sourceDir = sourceDir;
	}

	static int fileCount = 0;

	public static void main(String[] args) throws Exception {
		// pass the path to the file as a parameter
		File file = new File("/var/log/apache2/access.log.1");
		Scanner sc = new Scanner(file);
		String month, date = null, messageline = null, restmessage = "";
		int lineCount = 0;
		while (sc.hasNextLine()) {

			if (lineCount == 20) {

				createFile(messageline);
				lineCount = 0;

			}

			String s = sc.nextLine();
			// System.out.println(sc.nextLine());
			String[] log = s.split(" ");
			date = log[3].substring(1);
			month = log[3].substring(4, 7);
			DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
			TemporalAccessor accessor = parser.parse(month);
			date = date.replace(month, String.valueOf(accessor.get(ChronoField.MONTH_OF_YEAR)));
			for (int i = 6; i < log.length; i++) {
				restmessage += " " + log[i];

			}
			messageline += date + " " + log[5].substring(1) + restmessage + System.lineSeparator();
			lineCount++;
		}

	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

		try {
			Path targetFile = sourceDir.relativize(file);

			zos.putNextEntry(new ZipEntry(targetFile.toString()));

			byte[] bytes = Files.readAllBytes(file);
			zos.write(bytes, 0, bytes.length);
			zos.closeEntry();

		} catch (IOException ex) {
			System.err.println(ex);
		}

		return FileVisitResult.CONTINUE;
	}

	public static void createFile(String messageLine) {
		String s = "/home/kali/Desktop/Havelsan/Havelsan-HWS/HW2/log/" + "log" + fileCount + ".txt",
				filePath = "/home/kali/Desktop/Havelsan/Havelsan-HWS/HW2/log/";
		Path sourceDir = Paths.get(filePath);
		fileCount++;
		try {
			FileWriter fWriter = new FileWriter(s);
			fWriter.write(messageLine);
			fWriter.close();

			String zipFileName = filePath.concat(".zip");
			zos = new ZipOutputStream(new FileOutputStream(zipFileName));
			Files.walkFileTree(sourceDir, new ReadFileAndZip(sourceDir));
			zos.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
