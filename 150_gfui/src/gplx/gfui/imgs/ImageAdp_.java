/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.imgs; import gplx.*; import gplx.gfui.*;
import gplx.core.primitives.*;
import gplx.core.ios.*; /*IoStream*/ import gplx.core.ios.streams.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class ImageAdp_ {
	public static ImageAdp as_(Object obj) {return obj instanceof ImageAdp ? (ImageAdp)obj : null;}
	public static ImageAdp cast(Object obj) {try {return (ImageAdp)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, ImageAdp.class, obj);}}
	public static final    ImageAdp Null = new_(10, 10);
	public static ImageAdp new_(int width, int height) {
		//		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);	// JAVA: must be TYPE_INT_RGB or else ImageIO.write("bmp") will fail
		BufferedImage img = getCompatibleImage(width, height);
				return new ImageAdp_base(img);
	}
	public static ImageAdp txt_mem_(Io_url url, SizeAdp size) {return new ImageAdp_txt(url, size);}
	public static ImageAdp txt_fil_(Io_url url) {
		String raw = Io_mgr.Instance.LoadFilStr(url);			
		SizeAdp size = null;
		if		(String_.Eq(raw, ""))			size = SizeAdp_.Zero;
		else if	(String_.Eq(url.Ext(), ".svg")) size = SizeOf_svg(url);
		else									size = SizeAdp_.parse(raw);
		return new ImageAdp_txt(url, size);
	}
	public static SizeAdp SizeOf_svg(Io_url url) {return Gfui_svg_util.QuerySize(url);}
	public static ImageAdp file_(Io_url url) {
		if (url.EqNull()) throw Err_.new_wo_type("cannot load image from null url");
		if (String_.Eq(url.Info().Key(), IoUrlInfo_.Mem.Key())) return txt_fil_(url);
		if (!Io_mgr.Instance.ExistsFil(url)) return Null;

				BufferedImage img = null;
		try {
			File f = new File(url.Xto_api());
			img = ImageIO.read(f);
		}
		catch (IOException e) {throw Err_.new_exc(e, "ui", "image load failed", "url", url.Xto_api());}
//		FileInputStream istream = new FileInputStream(new File(url.Xto_api()));
//		JPEGImageDecoder dec = JPEGCodec.createJPEGDecoder(istream);
//		BufferedImage im = dec.decodeAsBufferedImage();
//		try {img = ImageIO.read(new File());}
//		catch (IOException e) {}
				return new ImageAdp_base(img).Url_(url);
	}
	    private static BufferedImage getCompatibleImage(int w, int h)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
//        System.out.println("compatible image type = " + image.getType());
        return image;
    }
    public static int MemorySize(ImageAdp image) {
		int areaInPixels = image.Height() * image.Width();
		BufferedImage bufferedImage = (BufferedImage)image.Under();
		int bitsPerPixel = BitsPerPixelCalc(bufferedImage.getType(), image.Url());
//		int bitsPerPixel = 4;
		return areaInPixels * (bitsPerPixel / 8);	// 8 bits per byte
	}
	static int BitsPerPixelCalc(int imageType, Io_url url) {	// REF:http://java.sun.com/j2se/1.4.2/docs/api/java/awt/image/BufferedImage.html
		if 		(imageType == BufferedImage.TYPE_3BYTE_BGR)			return 24;
		else if (imageType == BufferedImage.TYPE_4BYTE_ABGR)		return 32;
		else if (imageType == BufferedImage.TYPE_4BYTE_ABGR_PRE)	return 32;
		else if (imageType == BufferedImage.TYPE_BYTE_BINARY)		return 8;	//?
		else if (imageType == BufferedImage.TYPE_BYTE_GRAY)			return 8;	//?
		else if (imageType == BufferedImage.TYPE_BYTE_INDEXED)		return 8;	//?
		else if (imageType == BufferedImage.TYPE_CUSTOM)			return 8;	//?
		else if (imageType == BufferedImage.TYPE_INT_ARGB)			return 32;	//?
		else if (imageType == BufferedImage.TYPE_INT_ARGB_PRE)		return 32;	//?
		else if (imageType == BufferedImage.TYPE_INT_BGR)			return 32;	//?
		else if (imageType == BufferedImage.TYPE_INT_RGB)			return 32;	//?
		else if (imageType == BufferedImage.TYPE_USHORT_555_RGB)	return 16;	//?
		else if (imageType == BufferedImage.TYPE_USHORT_565_RGB)	return 16;	//?
		else if (imageType == BufferedImage.TYPE_USHORT_GRAY)		return 16;	//?
		else														{UsrDlg_.Instance.Warn("unknown bits per pixel", "imageType", imageType, "url", url.Xto_api()); return 8;}
	}
	}
class Gfui_svg_util {
	public static SizeAdp QuerySize(Io_url url) {
		try {
			// NOTE: not using XmlDoc b/c invalid doctypes can cause xml to hang; <?xml version="1.0" encoding="UTF-8" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.0//EN"	"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"><!-- Created with Inkscape (http://www.inkscape.org/) --> <svg xmlns="http://www.w3.org/2000/svg"
			String xml = Io_mgr.Instance.LoadFilStr(url);
			int pos = String_.FindFwd(xml, "<svg", 0); if (pos == -1) return null;
			Int_obj_ref pos_ref = Int_obj_ref.New(pos);
			double w = ParseAtr(xml, pos_ref, "width");
			double h = ParseAtr(xml, pos_ref, "height");
			return SizeAdp_.new_((int)w, (int)h);
		} catch (Exception e) {Err_.Noop(e); return SizeAdp_.Null;}
	}
	static double ParseAtr(String xml, Int_obj_ref pos_ref, String atr) {
		int pos = String_.FindFwd(xml, atr, pos_ref.Val()); if (pos == -1) return -1;
		int bgn = String_.FindFwd(xml, "\"", pos); if (bgn == -1) return -1;
		++bgn; // place after quote
		int end = String_.FindFwd(xml, "\"", bgn); if (end == -1) return -1;
		int px = String_.FindBwd(xml, "px", end);	// handle width="20px"
		if (px != -1) end = px;
		String str = String_.Mid(xml, bgn, end);
		pos_ref.Val_(end);
		return Double_.parse(str);
	}
}
