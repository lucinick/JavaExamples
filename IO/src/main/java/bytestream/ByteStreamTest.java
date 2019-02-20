package bytestream;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ByteStreamTest {

	@Test
	public void streamTest() throws IOException {
		InputStream is = new FileInputStream("input.txt");
		OutputStream os = new FileOutputStream("output.txt");
		
		int c;
		while((c = is.read()) != -1) {
			os.write(c);
		}
		File outputFile = new File("output.txt");
		boolean outputCreated = outputFile.exists();
		assertTrue("Output chua duoc tao", outputCreated);
		if (outputCreated) {
			outputFile.deleteOnExit();
		}
		is.close();
		os.close();
	}
	
	public void readerTest() {
		try {
			FileReader fr = new FileReader("input.txt");
			FileWriter fw = new FileWriter("output.txt");
			
			int c;
			while((c = fr.read()) != -1) {
				fw.write(c);
			}
			File outputFile = new File("output.txt");
			boolean outputCreated = outputFile.exists();
			assertTrue("Output chua duoc tao", outputCreated);
			if (outputCreated) {
				outputFile.deleteOnExit();
			}
			fr.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void printFromKeyboardTest() throws IOException {
		InputStream is = System.in;
		OutputStream os = System.out;
		
		int c;
		while((c = is.read()) != -1) {
			os.write(c);
			if (new String(new byte[1], 0, c).equals("q")) {
				break;
			}
		}
	}
	
	@Test
	public void checkExistBeforeFlushing() {
		byte[] b1 = new byte[]{'H','e','l','l','o',' ','w','o','r','l','d'};
		try (
				OutputStream os = new FileOutputStream("output.txt");
				InputStream is = new FileInputStream("output.txt");
				){
			os.write(b1);
			int c;
			byte[] b2 = new byte[10];
			String content = "";
			while((c = is.read(b2)) != -1) {
				content += c;
			}
			assertNotEquals("Content duoc truyen vao truoc khi flushing", content, "Hello world");
			os.flush();
			
			content = "";
			while((c = is.read(b2)) != -1) {
				content += c;
			}
			assertEquals("Content van chua duoc truyen vao sau khi flushing", content, "Hello world");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File file = new File("output.txt");
			if (file.exists()) {
				file.deleteOnExit();
			}
		}
		
		
	}
}
