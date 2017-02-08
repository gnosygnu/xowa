/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mws.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.xowa.mws.filerepo.file.*; import gplx.xowa.mws.parsers.lnkis.*;
import gplx.xowa.mws.utls.*;
/*	XO.TODO:
	* parseParamString
	* fitBoxWidth
	* scaleHeight
	* validateThumbParams
*/
// MEMORY:only one instance per wiki
public abstract class Xomw_ImageHandler extends Xomw_MediaHandler {	private final    Xomw_param_map paramMap = new Xomw_param_map();
	public Xomw_ImageHandler(byte[] key) {super(key);
		paramMap.Add(Xomw_param_itm.Mw__img_width, Xomw_param_map.Type__handler, Xomw_param_itm.Name_bry__width);
	}
	/**
	* @param File file
	* @return boolean
	*/
	@Override public boolean canRender(Xomw_File file) {
		return (Php_utl_.istrue(file.getWidth()) && Php_utl_.istrue(file.getHeight()));
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
		if (Php_utl_.isset(handlerParams.physicalWidth)) {
			width = handlerParams.physicalWidth;
		}
		else if (Php_utl_.isset(handlerParams.width)) {
			width = handlerParams.width;
		}
		else {
			throw Err_.new_wo_type("No width specified to makeParamString");
		}

		// Removed for ProofreadPage
		// width = intval(width);
		return Bry_.Add(Int_.To_bry(width), Xomw_lnki_wkr.Bry__px);
	}

//		public function parseParamString(str) {
//			m = false;
//			if (preg_match('/^(\d+)px/', str, m)) {
//				return [ 'width' => m[1] ];
//			} else {
//				return false;
//			}
//		}

//		function getScriptParams(paramsVar) {
//			return [ 'width' => paramsVar['width'] ];
//		}

	/**
	* @param File image
	* @param array paramsVar
	* @return boolean
	*/
	@Override public boolean normaliseParams(Xomw_File image, Xomw_params_handler handlerParams) {
//			mimeType = image.getMimeType();

		if (!Php_utl_.isset(handlerParams.width)) {
			return false;
		}

		if (!Php_utl_.isset(handlerParams.page)) {
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

		if (Php_utl_.isset(handlerParams.height) && handlerParams.height != -1) {
			// Height & width were both set
			if (handlerParams.width * srcHeight > handlerParams.height * srcWidth) {
				// Height is the relative smaller dimension, so scale width accordingly
//					handlerParams.width = self::fitBoxWidth(srcWidth, srcHeight, handlerParams.height);

				if (handlerParams.width == 0) {
					// Very small image, so we need to rely on client side scaling :(
					handlerParams.width = 1;
				}

				handlerParams.physicalWidth = handlerParams.width;
			} else {
				// Height was crap, unset it so that it will be calculated later
				handlerParams.height = Php_utl_.Null_int;
			}
		}

		if (!Php_utl_.isset(handlerParams.physicalWidth)) {
			// Passed all validations, so set the physicalWidth
			handlerParams.physicalWidth = handlerParams.width;
		}

		// Because thumbs are only referred to by width, the height always needs
		// to be scaled by the width to keep the thumbnail sizes consistent,
		// even if it was set inside the if block above
//			handlerParams.physicalHeight = File::scaleHeight(srcWidth, srcHeight,
//				handlerParams.physicalWidth);

		// Set the height if it was not validated in the if block higher up
		if (!Php_utl_.isset(handlerParams.height) || handlerParams.height == -1) {
			handlerParams.height = handlerParams.physicalHeight;
		}

//			if (!this.validateThumbParams(handlerParams.physicalWidth,
//				handlerParams.physicalHeight, srcWidth, srcHeight, mimeType)
//			) {
//				return false;
//			}

		return true;
	}

//		/**
//		* Validate thumbnail parameters and fill in the correct height
//		*
//		* @param int width Specified width (input/output)
//		* @param int height Height (output only)
//		* @param int srcWidth Width of the source image
//		* @param int srcHeight Height of the source image
//		* @param String mimeType Unused
//		* @return boolean False to indicate that an error should be returned to the user.
//		*/
//		function validateThumbParams(&width, &height, srcWidth, srcHeight, mimeType) {
//			width = intval(width);
//
//			# Sanity check width
//			if (width <= 0) {
//				wfDebug(__METHOD__ . ": Invalid destination width: width\n");
//
//				return false;
//			}
//			if (srcWidth <= 0) {
//				wfDebug(__METHOD__ . ": Invalid source width: srcWidth\n");
//
//				return false;
//			}
//
//			height = File::scaleHeight(srcWidth, srcHeight, width);
//			if (height == 0) {
//				# Force height to be at least 1 pixel
//				height = 1;
//			}
//
//			return true;
//		}
//
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
