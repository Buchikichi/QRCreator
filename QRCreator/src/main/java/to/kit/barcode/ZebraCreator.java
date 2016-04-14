package to.kit.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;

/**
 * ZebraCreator
 * @author H.Sasai
 */
public class ZebraCreator {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ZebraCreator.class);
	private CheckDigit e13 = EAN13CheckDigit.EAN13_CHECK_DIGIT;

	private String calculate(final String code) {
		String result = code;

		try {
			result = code + this.e13.calculate(code);
			LOG.debug("result:{}", result);
			LOG.debug("e13:" + this.e13.isValid(result));
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void create(final String contents) throws IOException, WriterException {
		int width = 160;
		int height = 30;
		String calculated = calculate(contents);
		BarcodeFormat format = BarcodeFormat.CODABAR;
		CodaBarWriter writer = new CodaBarWriter();
		String filename = calculated.replaceAll("[/]", "_");

		BitMatrix bitMatrix = writer.encode(calculated, format, width, height);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ImageIO.write(image, "png", new File(filename + ".png"));
	}

	public String inputContent() {
		return JOptionPane.showInputDialog("入力してください", "1234567890123");
	}

	public static void main(String[] args) throws Exception {
		ZebraCreator app = new ZebraCreator();
		String contents;

		if (0 < args.length) {
			contents = args[0];
		} else {
			contents = app.inputContent();
		}
		if (contents == null || contents.trim().isEmpty()) {
			return;
		}
		app.create(contents);
	}
}
