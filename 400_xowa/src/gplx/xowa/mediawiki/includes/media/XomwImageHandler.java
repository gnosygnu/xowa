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
package gplx.xowa.mediawiki.includes.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.filerepo.file.*; import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
/*	XO.TODO:
	* validateThumbParams
*/
// MEMORY:only one instance per wiki
public abstract class XomwImageHandler extends XomwMediaHandler {	private final    Xomw_param_map paramMap = new Xomw_param_map();
	public XomwImageHandler(byte[] key) {super(key);
		paramMap.Add(Xomw_param_itm.Mw__img_width, Xomw_param_map.Type__handler, Xomw_param_itm.Name_bry__width);
	}
	/**
	* @param File file
	* @return boolean
	*/
	@Override public boolean canRender(XomwFile file) {
		return (XophpUtility.istrue(file.getWidth()) && XophpUtility.istrue(file.getHeight()));
	}

	@Override public Xomw_param_map getParamMap() {
		// XO.MW: defined above: "return [ 'img_width' => 'width' ];"
		return paramMap;
	}

	@Override public boolean validateParam(int name_uid, byte[] val_bry, int val_int) {
		if (name_uid == Xomw_param_itm.Name__width || name_uid == Xomw_param_itm.Name__height) {
			if (val_int <= 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}

	@Override public byte[] makeParamString(Xomw_params_handler handlerParams) {
		int width = 0;
		if (XophpUtility.isset(handlerParams.physicalWidth)) {
			width = handlerParams.physicalWidth;
		}
		else if (XophpUtility.isset(handlerParams.width)) {
			width = handlerParams.width;
		}
		else {
			throw Err_.new_wo_type("No width specified to makeParamString");
		}

		// Removed for ProofreadPage
		// width = intval(width);
		return Bry_.Add(Int_.To_bry(width), Xomw_lnki_wkr.Bry__px);
	}

//		public Xomw_param_map parseParamString(byte[] src) {
//			int len = src.length;
//			// XO.MW.REGEX: if (preg_match('/^(\d+)px/', str, m)) {
//			if (    len > 0                                               // at least one char
//				&&  Byte_ascii.Is_num(src[0]))                            // 1st char is numeric
//			{
//				pos = Bry_find_.Find_fwd_while_num(src, 1, len);          // skip numeric
//				if (Bry_.Match(src, pos, len, Xomw_lnki_wkr.Bry__px)) {   // matches "px"
//					Xomw_params_handler rv = new Xomw_params_handler();
//					rv.width = Bry_.To_int_or(src, 0, pos, XophpUtility.NULL_INT);
//					return rv;
//				}
//			}
//			return null;
//		}

//		function getScriptParams(paramsVar) {
//			return [ 'width' => paramsVar['width'] ];
//		}

	/**
	* @param File image
	* @param array paramsVar
	* @return boolean
	*/
	@Override public boolean normaliseParams(XomwFile image, Xomw_params_handler handlerParams) {
		byte[] mimeType = image.getMimeType();

		if (!XophpUtility.isset(handlerParams.width)) {
			return false;
		}

		if (!XophpUtility.isset(handlerParams.page)) {
			handlerParams.page = 1;
		}
		else {
			// handlerParams.page = intval(handlerParams.page);
//				if (handlerParams.page > image.pageCount()) {
//					handlerParams.page = image.pageCount();
//				}
//
//				if (handlerParams.page < 1) {
//					handlerParams.page = 1;
//				}
		}

		int srcWidth = image.getWidth(handlerParams.page);
		int srcHeight = image.getHeight(handlerParams.page);

		if (XophpUtility.isset(handlerParams.height) && handlerParams.height != -1) {
			// Height & width were both set
			if (handlerParams.width * srcHeight > handlerParams.height * srcWidth) {
				// Height is the relative smaller dimension, so scale width accordingly
				handlerParams.width = fitBoxWidth(srcWidth, srcHeight, handlerParams.height);

				if (handlerParams.width == 0) {
					// Very small image, so we need to rely on client side scaling :(
					handlerParams.width = 1;
				}

				handlerParams.physicalWidth = handlerParams.width;
			} else {
				// Height was crap, unset it so that it will be calculated later
				handlerParams.height = XophpUtility.NULL_INT;
			}
		}

		if (!XophpUtility.isset(handlerParams.physicalWidth)) {
			// Passed all validations, so set the physicalWidth
			handlerParams.physicalWidth = handlerParams.width;
		}

		// Because thumbs are only referred to by width, the height always needs
		// to be scaled by the width to keep the thumbnail sizes consistent,
		// even if it was set inside the if block above
		handlerParams.physicalHeight = XomwFile.scaleHeight(srcWidth, srcHeight,
			handlerParams.physicalWidth);

		// Set the height if it was not validated in the if block higher up
		if (!XophpUtility.isset(handlerParams.height) || handlerParams.height == -1) {
			handlerParams.height = handlerParams.physicalHeight;
		}

		if (!this.validateThumbParams(handlerParams, srcWidth, srcHeight, mimeType)
		) {
			return false;
		}

		return true;
	}

	/**
	* Validate thumbnail parameters and fill in the correct height
	*
	* @param int width Specified width (input/output)
	* @param int height Height (output only)
	* @param int srcWidth Width of the source image
	* @param int srcHeight Height of the source image
	* @param String mimeType Unused
	* @return boolean False to indicate that an error should be returned to the user.
	*/
	// XO.MW.NOTE: MW passes w and h by ref, but only changes h; XO will pass handlerParams directly
	private boolean validateThumbParams(Xomw_params_handler handlerParams, int srcWidth, int srcHeight, byte[] mimeType) {
		int width = handlerParams.physicalWidth;
		int height = handlerParams.physicalHeight;
		// width = intval(width);

		// Sanity check width
		if (width <= 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "validateThumbParams: Invalid destination width: width");

			return false;
		}
		if (srcWidth <= 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "validateThumbParams: Invalid source width: srcWidth");

			return false;
		}

		height = XomwFile.scaleHeight(srcWidth, srcHeight, width);
		if (height == 0) {
			// Force height to be at least 1 pixel
			height = 1;
		}
		handlerParams.height = height;

		return true;
	}

//		/**
//		* @param File image
//		* @param String script
//		* @param array paramsVar
//		* @return boolean|MediaTransformOutput
//		*/
//		function getScriptedTransform(image, script, paramsVar) {
//			if (!this.normaliseParams(image, paramsVar)) {
//				return false;
//			}
//			url = wfAppendQuery(script, this.getScriptParams(paramsVar));
//
//			if (image.mustRender() || paramsVar['width'] < image.getWidth()) {
//				return new ThumbnailImage(image, url, false, paramsVar);
//			}
//		}
//
//		function getImageSize(image, path) {
//			MediaWiki\suppressWarnings();
//			gis = getimagesize(path);
//			MediaWiki\restoreWarnings();
//
//			return gis;
//		}
//
//		/**
//		* Function that returns the number of pixels to be thumbnailed.
//		* Intended for animated GIFs to multiply by the number of frames.
//		*
//		* If the file doesn't support a notion of "area" return 0.
//		*
//		* @param File image
//		* @return int
//		*/
//		function getImageArea(image) {
//			return image.getWidth() * image.getHeight();
//		}
//
//		/**
//		* @param File file
//		* @return String
//		*/
//		function getShortDesc(file) {
//			global wgLang;
//			nbytes = htmlspecialchars(wgLang.formatSize(file.getSize()));
//			widthheight = wfMessage('widthheight')
//				.numParams(file.getWidth(), file.getHeight()).escaped();
//
//			return "widthheight (nbytes)";
//		}
//
//		/**
//		* @param File file
//		* @return String
//		*/
//		function getLongDesc(file) {
//			global wgLang;
//			pages = file.pageCount();
//			size = htmlspecialchars(wgLang.formatSize(file.getSize()));
//			if (pages === false || pages <= 1) {
//				msg = wfMessage('file-info-size').numParams(file.getWidth(),
//					file.getHeight()).paramsVar(size,
//						'<span class="mime-type">' . file.getMimeType() . '</span>').parse();
//			} else {
//				msg = wfMessage('file-info-size-pages').numParams(file.getWidth(),
//					file.getHeight()).paramsVar(size,
//						'<span class="mime-type">' . file.getMimeType() . '</span>').numParams(pages).parse();
//			}
//
//			return msg;
//		}
//
//		/**
//		* @param File file
//		* @return String
//		*/
//		function getDimensionsString(file) {
//			pages = file.pageCount();
//			if (pages > 1) {
//				return wfMessage('widthheightpage')
//					.numParams(file.getWidth(), file.getHeight(), pages).text();
//			} else {
//				return wfMessage('widthheight')
//					.numParams(file.getWidth(), file.getHeight()).text();
//			}
//		}
//
//		public function sanitizeParamsForBucketing(paramsVar) {
//			paramsVar = parent::sanitizeParamsForBucketing(paramsVar);
//
//			// We unset the height parameters in order to let normaliseParams recalculate them
//			// Otherwise there might be a height discrepancy
//			if (isset(paramsVar['height'])) {
//				unset(paramsVar['height']);
//			}
//
//			if (isset(paramsVar['physicalHeight'])) {
//				unset(paramsVar['physicalHeight']);
//			}
//
//			return paramsVar;
//		}
}
