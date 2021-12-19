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
package gplx.xowa.mediawiki.includes.media;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp_bry;
// XO.MW:MW has registry and instance cache; XO only has instance
// XO.MW:SYNC:1.29; DATE:2017-02-05
public class XomwMediaHandlerFactory {
	private final Hash_adp_bry handlers = Hash_adp_bry.cs();

	// XO.MW:SYNC:1.29; DATE:2017-02-05
	public XomwMediaHandlerFactory() {
		// Default, MediaWiki core media handlers
//			'image/jpeg' => JpegHandler::class,
		handlers.Add(Mime__image__png, new XomwTransformationalImageHandler(Mime__image__png));	// PngHandler
//			'image/gif' => GIFHandler::class,
//			'image/tiff' => TiffHandler::class,
//			'image/webp' => WebPHandler::class,
//			'image/x-ms-bmp' => BmpHandler::class,
//			'image/x-bmp' => BmpHandler::class,
//			'image/x-xcf' => XCFHandler::class,
//			'image/svg+xml' => SvgHandler::class, // official
//			'image/svg' => SvgHandler::class, // compat
//			'image/vnd.djvu' => DjVuHandler::class, // official
//			'image/x.djvu' => DjVuHandler::class, // compat
//			'image/x-djvu' => DjVuHandler::class, // compat

	}

	// XO.MW:SYNC:1.29; DATE:2017-02-05
	public XomwMediaHandler getHandler(byte[] type) {
		return (XomwMediaHandler)handlers.GetByOrNull(type);
	}

	public static byte[]
	  Mime__image__jpeg                    = BryUtl.NewA7("image/jpeg")
	, Mime__image__png                     = BryUtl.NewA7("image/png")
	, Mime__image__gif                     = BryUtl.NewA7("image/gif")
	, Mime__image__tiff                    = BryUtl.NewA7("image/tiff")
	, Mime__image__webp                    = BryUtl.NewA7("image/webp")
	, Mime__image__x_ms_bmp                = BryUtl.NewA7("image/x-ms-bmp")
	, Mime__image__x_bmp                   = BryUtl.NewA7("image/x-bmp")
	, Mime__image__x_xcf                   = BryUtl.NewA7("image/x-xcf")
	, Mime__image__svg_xml                 = BryUtl.NewA7("image/svg+xml")
	, Mime__image__svg                     = BryUtl.NewA7("image/svg")
	, Mime__image__vnd_djvu                = BryUtl.NewA7("image/vnd.djvu")
	, Mime__image__x_djvu_dot              = BryUtl.NewA7("image/x.djvu")
	, Mime__image__x_djvu_dash             = BryUtl.NewA7("image/x-djvu")
	;
}
