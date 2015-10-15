package to.kit.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * QRCreator
 * @author H.Sasai
 */
public class QRCreator {
	public void create(String contents) throws IOException, WriterException {
		int width = 320;
		int height = 320;
		BarcodeFormat format = BarcodeFormat.QR_CODE;
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		QRCodeWriter writer = new QRCodeWriter();

		hints.put(EncodeHintType.CHARACTER_SET, "Shift_JIS");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		BitMatrix bitMatrix = writer.encode(contents, format, width, height, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ImageIO.write(image, "png", new File(contents + ".png"));
	}

	public String inputContent() {
		return JOptionPane.showInputDialog("入力してください", "にほんご");
	}

	public static void main(String[] args) throws Exception {
		QRCreator app = new QRCreator();
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
