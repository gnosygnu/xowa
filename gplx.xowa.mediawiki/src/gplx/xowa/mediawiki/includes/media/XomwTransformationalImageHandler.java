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
import gplx.xowa.mediawiki.includes.filerepo.file.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
public class XomwTransformationalImageHandler extends XomwImageHandler {	public XomwTransformationalImageHandler(byte[] key) {super(key);
	}

	/**
	* @param File image
	* @param array paramsVar Transform parameters. Entries with the keys 'width'
	* and 'height' are the respective screen width and height, while the keys
	* 'physicalWidth' and 'physicalHeight' indicate the thumbnail dimensions.
	* @return boolean
	*/
	@Override public boolean normaliseParams(XomwFile image, Xomw_params_handler prms) {
		if (!super.normaliseParams(image, prms)) {
			return false;
		}

		// Obtain the source, pre-rotation dimensions
		int srcWidth = image.getWidth(prms.page);
		int srcHeight = image.getHeight(prms.page);

		// Don't make an image bigger than the source
		if (prms.physicalWidth >= srcWidth) {
			prms.physicalWidth = srcWidth;
			prms.physicalHeight = srcHeight;

			// Skip scaling limit checks if no scaling is required
			// due to requested size being bigger than source.
			if (!image.mustRender()) {
				return true;
			}
		}

		return true;
	}

//		/**
//		* Extracts the width/height if the image will be scaled before rotating
//		*
//		* This will match the physical size/aspect ratio of the original image
//		* prior to application of the rotation -- so for a portrait image that's
//		* stored as raw landscape with 90-degress rotation, the resulting size
//		* will be wider than it is tall.
//		*
//		* @param array paramsVar Parameters as returned by normaliseParams
//		* @param int rotation The rotation angle that will be applied
//		* @return array (width, height) array
//		*/
//		public function extractPreRotationDimensions(paramsVar, rotation) {
//			if (rotation == 90 || rotation == 270) {
//				// We'll resize before rotation, so swap the dimensions again
//				width = paramsVar['physicalHeight'];
//				height = paramsVar['physicalWidth'];
//			} else {
//				width = paramsVar['physicalWidth'];
//				height = paramsVar['physicalHeight'];
//			}
//
//			return [ width, height ];
//		}
//
	/**
	* Create a thumbnail.
	*
	* This sets up various parameters, and then calls a helper method
	* based on this.getScalerType in order to scale the image.
	*
	* @param File image
	* @param String dstPath
	* @param String dstUrl
	* @param array paramsVar
	* @param int flags
	* @return MediaTransformError|ThumbnailImage|TransformParameterError
	*/
	@Override public XomwMediaTransformOutput doTransform(XomwFile image, byte[] dstPath, byte[] dstUrl, Xomw_params_handler prms, int flags) {
//			if (!this.normaliseParams(image, paramsVar)) {
//				return new TransformParameterError(paramsVar);
//			}
//
//			// Create a parameter array to pass to the scaler
		Xomw_params_scalar scalerParams = new Xomw_params_scalar();
//			// The size to which the image will be resized
		scalerParams.physicalWidth = prms.physicalWidth;
		scalerParams.physicalHeight = prms.physicalHeight;
//			'physicalDimensions' => "{paramsVar['physicalWidth']}x{paramsVar['physicalHeight']}",
		// The size of the image on the page
		scalerParams.clientWidth = prms.width;
		scalerParams.clientHeight = prms.height;
		// Comment as will be added to the Exif of the thumbnail
//			'comment' => isset(paramsVar['descriptionUrl'])
//				? "File source: {paramsVar['descriptionUrl']}"
//				: '',
		// Properties of the original image
		scalerParams.srcWidth = image.getWidth();
		scalerParams.srcHeight = image.getHeight();
		scalerParams.mimeType = image.getMimeType();
		scalerParams.dstPath = dstPath;
		scalerParams.dstUrl = dstUrl;
//			'interlace' => isset(paramsVar['interlace']) ? paramsVar['interlace'] : false,

//			if (isset(paramsVar['quality']) && paramsVar['quality'] === 'low') {
//				scalerParams['quality'] = 30;
//			}

		// For subclasses that might be paged.
//			if (image.isMultipage() && isset(paramsVar['page'])) {
//				scalerParams['page'] = intval(paramsVar['page']);
//			}

		// Determine scaler type
//			scaler = this.getScalerType(dstPath);
//
//			if (is_array(scaler)) {
//				scalerName = get_class(scaler[0]);
//			} else {
//				scalerName = scaler;
//			}
//
//			wfDebug(__METHOD__ . ": creating {scalerParams['physicalDimensions']} " .
//				"thumbnail at dstPath using scaler scalerName\n");

		if (!image.mustRender() &&
			scalerParams.physicalWidth == scalerParams.srcWidth
			&& scalerParams.physicalHeight == scalerParams.srcHeight
//				&& !isset(scalerParams['quality'])
		) {

			// normaliseParams (or the user) wants us to return the unscaled image
//				wfDebug(__METHOD__ . ": returning unscaled image\n");

			return this.getClientScalingThumbnailImage(image, scalerParams);
		}

//			if (scaler == 'client') {
//				// Client-side image scaling, use the source URL
//				// Using the destination URL in a TRANSFORM_LATER request would be incorrect
//				return this.getClientScalingThumbnailImage(image, scalerParams);
//			}
//
//			if (image.isTransformedLocally() && !this.isImageAreaOkForThumbnaling(image, paramsVar)) {
//				global wgMaxImageArea;
//				return new TransformTooBigImageAreaError(paramsVar, wgMaxImageArea);
//			}
//
//			if (flags & self::TRANSFORM_LATER) {
//				wfDebug(__METHOD__ . ": Transforming later per flags.\n");
//				newParams = [
//					'width' => scalerParams['clientWidth'],
//					'height' => scalerParams['clientHeight']
//				];
//				if (isset(paramsVar['quality'])) {
//					newParams['quality'] = paramsVar['quality'];
//				}
//				if (isset(paramsVar['page']) && paramsVar['page']) {
//					newParams['page'] = paramsVar['page'];
//				}
//				return new XomwThumbnailImage(image, dstUrl, null, newParams);
			return new XomwThumbnailImage(image, dstUrl, null, prms);
//			}
//
//			// Try to make a target path for the thumbnail
//			if (!wfMkdirParents(dirname(dstPath), null, __METHOD__)) {
//				wfDebug(__METHOD__ . ": Unable to create thumbnail destination " .
//					"directory, falling back to client scaling\n");
//
//				return this.getClientScalingThumbnailImage(image, scalerParams);
//			}
//
//			// Transform functions and binaries need a FS source file
//			thumbnailSource = this.getThumbnailSource(image, paramsVar);
//
//			// If the source isn't the original, disable EXIF rotation because it's already been applied
//			if (scalerParams['srcWidth'] != thumbnailSource['width']
//				|| scalerParams['srcHeight'] != thumbnailSource['height']) {
//				scalerParams['disableRotation'] = true;
//			}
//
//			scalerParams['srcPath'] = thumbnailSource['path'];
//			scalerParams['srcWidth'] = thumbnailSource['width'];
//			scalerParams['srcHeight'] = thumbnailSource['height'];
//
//			if (scalerParams['srcPath'] === false) { // Failed to get local copy
//				wfDebugLog('thumbnail',
//					sprintf('Thumbnail failed on %s: could not get local copy of "%s"',
//						wfHostname(), image.getName()));
//
//				return new MediaTransformError('thumbnail_error',
//					scalerParams['clientWidth'], scalerParams['clientHeight'],
//					wfMessage('filemissing')
//				);
//			}
//
//			// Try a hook. Called "Bitmap" for historical reasons.
//			/** @var mto MediaTransformOutput */
//			mto = null;
//			Hooks::run('BitmapHandlerTransform', [ this, image, &scalerParams, &mto ]);
//			if (!is_null(mto)) {
//				wfDebug(__METHOD__ . ": Hook to BitmapHandlerTransform created an mto\n");
//				scaler = 'hookaborted';
//			}
//
//			// scaler will return a MediaTransformError on failure, or false on success.
//			// If the scaler is succesful, it will have created a thumbnail at the destination
//			// path.
//			if (is_array(scaler) && is_callable(scaler)) {
//				// Allow subclasses to specify their own rendering methods.
//				err = call_user_func(scaler, image, scalerParams);
//			} else {
//				switch (scaler) {
//					case 'hookaborted':
//						// Handled by the hook above
//						err = mto.isError() ? mto : false;
//						break;
//					case 'im':
//						err = this.transformImageMagick(image, scalerParams);
//						break;
//					case 'custom':
//						err = this.transformCustom(image, scalerParams);
//						break;
//					case 'imext':
//						err = this.transformImageMagickExt(image, scalerParams);
//						break;
//					case 'gd':
//					default:
//						err = this.transformGd(image, scalerParams);
//						break;
//				}
//			}
//
//			// Remove the file if a zero-byte thumbnail was created, or if there was an error
//			removed = this.removeBadFile(dstPath, (boolean)err);
//			if (err) {
//				// transform returned MediaTransforError
//				return err;
//			} elseif (removed) {
//				// Thumbnail was zero-byte and had to be removed
//				return new MediaTransformError('thumbnail_error',
//					scalerParams['clientWidth'], scalerParams['clientHeight'],
//					wfMessage('unknown-error')
//				);
//			} elseif (mto) {
//				return mto;
//			} else {
//				newParams = [
//					'width' => scalerParams['clientWidth'],
//					'height' => scalerParams['clientHeight']
//				];
//				if (isset(paramsVar['quality'])) {
//					newParams['quality'] = paramsVar['quality'];
//				}
//				if (isset(paramsVar['page']) && paramsVar['page']) {
//					newParams['page'] = paramsVar['page'];
//				}
//				return new ThumbnailImage(image, dstUrl, dstPath, newParams);
//			}
//			return null;
	}

//		/**
//		* Get the source file for the transform
//		*
//		* @param File file
//		* @param array paramsVar
//		* @return array Array with keys  width, height and path.
//		*/
//		protected function getThumbnailSource(file, paramsVar) {
//			return file.getThumbnailSource(paramsVar);
//		}
//
//		/**
//		* Returns what sort of scaler type should be used.
//		*
//		* Values can be one of client, im, custom, gd, imext, or an array
//		* of Object, method-name to call that specific method.
//		*
//		* If specifying a custom scaler command with [ Obj, method ],
//		* the method in question should take 2 parameters, a File Object,
//		* and a scalerParams array with various options (See doTransform
//		* for what is in scalerParams). On error it should return a
//		* MediaTransformError Object. On success it should return false,
//		* and simply make sure the thumbnail file is located at
//		* scalerParams['dstPath'].
//		*
//		* If there is a problem with the output path, it returns "client"
//		* to do client side scaling.
//		*
//		* @param String dstPath
//		* @param boolean checkDstPath Check that dstPath is valid
//		* @return String|Callable One of client, im, custom, gd, imext, or a Callable array.
//		*/
//		abstract protected function getScalerType(dstPath, checkDstPath = true);

	/**
	* Get a ThumbnailImage that respresents an image that will be scaled
	* client side
	*
	* @param File image File associated with this thumbnail
	* @param array scalerParams Array with scaler paramsVar
	* @return ThumbnailImage
	*
	* @todo FIXME: No rotation support
	*/
	private XomwThumbnailImage getClientScalingThumbnailImage(XomwFile image, Xomw_params_scalar scalerParams) {
		Xomw_params_handler prms = new Xomw_params_handler();
		prms.width = scalerParams.clientWidth;
		prms.height = scalerParams.clientHeight;

		return new XomwThumbnailImage(image, image.getUrl(), null, prms);
	}

//		/**
//		* Transform an image using ImageMagick
//		*
//		* This is a stub method. The real method is in BitmapHander.
//		*
//		* @param File image File associated with this thumbnail
//		* @param array paramsVar Array with scaler paramsVar
//		*
//		* @return MediaTransformError Error Object if error occurred, false (=no error) otherwise
//		*/
//		protected function transformImageMagick(image, paramsVar) {
//			return this.getMediaTransformError(paramsVar, "Unimplemented");
//		}
//
//		/**
//		* Transform an image using the Imagick PHP extension
//		*
//		* This is a stub method. The real method is in BitmapHander.
//		*
//		* @param File image File associated with this thumbnail
//		* @param array paramsVar Array with scaler paramsVar
//		*
//		* @return MediaTransformError Error Object if error occurred, false (=no error) otherwise
//		*/
//		protected function transformImageMagickExt(image, paramsVar) {
//			return this.getMediaTransformError(paramsVar, "Unimplemented");
//		}
//
//		/**
//		* Transform an image using a custom command
//		*
//		* This is a stub method. The real method is in BitmapHander.
//		*
//		* @param File image File associated with this thumbnail
//		* @param array paramsVar Array with scaler paramsVar
//		*
//		* @return MediaTransformError Error Object if error occurred, false (=no error) otherwise
//		*/
//		protected function transformCustom(image, paramsVar) {
//			return this.getMediaTransformError(paramsVar, "Unimplemented");
//		}
//
//		/**
//		* Get a MediaTransformError with error 'thumbnail_error'
//		*
//		* @param array paramsVar Parameter array as passed to the transform* functions
//		* @param String errMsg Error message
//		* @return MediaTransformError
//		*/
//		public function getMediaTransformError(paramsVar, errMsg) {
//			return new MediaTransformError('thumbnail_error', paramsVar['clientWidth'],
//				paramsVar['clientHeight'], errMsg);
//		}
//
//		/**
//		* Transform an image using the built in GD library
//		*
//		* This is a stub method. The real method is in BitmapHander.
//		*
//		* @param File image File associated with this thumbnail
//		* @param array paramsVar Array with scaler paramsVar
//		*
//		* @return MediaTransformError Error Object if error occurred, false (=no error) otherwise
//		*/
//		protected function transformGd(image, paramsVar) {
//			return this.getMediaTransformError(paramsVar, "Unimplemented");
//		}
//
//		/**
//		* Escape a String for ImageMagick's property input (e.g. -set -comment)
//		* See InterpretImageProperties() in magick/property.c
//		* @param String s
//		* @return String
//		*/
//		function escapeMagickProperty(s) {
//			// Double the backslashes
//			s = str_replace('\\', '\\\\', s);
//			// Double the percents
//			s = str_replace('%', '%%', s);
//			// Escape initial - or @
//			if (strlen(s) > 0 && (s[0] === '-' || s[0] === '@')) {
//				s = '\\' . s;
//			}
//
//			return s;
//		}
//
//		/**
//		* Escape a String for ImageMagick's input filenames. See ExpandFilenames()
//		* and GetPathComponent() in magick/utility.c.
//		*
//		* This won't work with an initial ~ or @, so input files should be prefixed
//		* with the directory name.
//		*
//		* Glob character unescaping is broken in ImageMagick before 6.6.1-5, but
//		* it's broken in a way that doesn't involve trying to convert every file
//		* in a directory, so we're better off escaping and waiting for the bugfix
//		* to filter down to users.
//		*
//		* @param String path The file path
//		* @param boolean|String scene The scene specification, or false if there is none
//		* @throws MWException
//		* @return String
//		*/
//		function escapeMagickInput(path, scene = false) {
//			// Die on initial metacharacters (caller should prepend path)
//			firstChar = substr(path, 0, 1);
//			if (firstChar === '~' || firstChar === '@') {
//				throw new MWException(__METHOD__ . ': cannot escape this path name');
//			}
//
//			// Escape glob chars
//			path = preg_replace('/[*?\[\]{}]/', '\\\\\0', path);
//
//			return this.escapeMagickPath(path, scene);
//		}
//
//		/**
//		* Escape a String for ImageMagick's output filename. See
//		* InterpretImageFilename() in magick/image.c.
//		* @param String path The file path
//		* @param boolean|String scene The scene specification, or false if there is none
//		* @return String
//		*/
//		function escapeMagickOutput(path, scene = false) {
//			path = str_replace('%', '%%', path);
//
//			return this.escapeMagickPath(path, scene);
//		}
//
//		/**
//		* Armour a String against ImageMagick's GetPathComponent(). This is a
//		* helper function for escapeMagickInput() and escapeMagickOutput().
//		*
//		* @param String path The file path
//		* @param boolean|String scene The scene specification, or false if there is none
//		* @throws MWException
//		* @return String
//		*/
//		protected function escapeMagickPath(path, scene = false) {
//			// Die on format specifiers (other than drive letters). The regex is
//			// meant to match all the formats you get from "convert -list format"
//			if (preg_match('/^([a-zA-Z0-9-]+):/', path, m)) {
//				if (wfIsWindows() && is_dir(m[0])) {
//					// OK, it's a drive letter
//					// ImageMagick has a similar exception, see IsMagickConflict()
//				} else {
//					throw new MWException(__METHOD__ . ': unexpected colon character in path name');
//				}
//			}
//
//			// If there are square brackets, add a do-nothing scene specification
//			// to force a literal interpretation
//			if (scene === false) {
//				if (strpos(path, '[') !== false) {
//					path .= '[0--1]';
//				}
//			} else {
//				path .= "[scene]";
//			}
//
//			return path;
//		}
//
//		/**
//		* Retrieve the version of the installed ImageMagick
//		* You can use PHPs version_compare() to use this value
//		* Value is cached for one hour.
//		* @return String|boolean Representing the IM version; false on error
//		*/
//		protected function getMagickVersion() {
//			cache = MediaWikiServices::getInstance().getLocalServerObjectCache();
//			method = __METHOD__;
//			return cache.getWithSetCallback(
//				'imagemagick-version',
//				cache::TTL_HOUR,
//				function () use (method) {
//					global wgImageMagickConvertCommand;
//
//					cmd = wfEscapeShellArg(wgImageMagickConvertCommand) . ' -version';
//					wfDebug(method . ": Running convert -version\n");
//					retval = '';
//					return = wfShellExec(cmd, retval);
//					x = preg_match(
//						'/Version: ImageMagick ([0-9]*\.[0-9]*\.[0-9]*)/', return, matches
//					);
//					if (x != 1) {
//						wfDebug(method . ": ImageMagick version check failed\n");
//						return false;
//					}
//
//					return matches[1];
//				}
//			);
//		}
//
//		/**
//		* Returns whether the current scaler supports rotation.
//		*
//		* @since 1.24 No longer static
//		* @return boolean
//		*/
//		public function canRotate() {
//			return false;
//		}
//
//		/**
//		* Should we automatically rotate an image based on exif
//		*
//		* @since 1.24 No longer static
//		* @see wgEnableAutoRotation
//		* @return boolean Whether auto rotation is enabled
//		*/
//		public function autoRotateEnabled() {
//			return false;
//		}
//
//		/**
//		* Rotate a thumbnail.
//		*
//		* This is a stub. See BitmapHandler::rotate.
//		*
//		* @param File file
//		* @param array paramsVar Rotate parameters.
//		*   'rotation' clockwise rotation in degrees, allowed are multiples of 90
//		* @since 1.24 Is non-static. From 1.21 it was static
//		* @return boolean|MediaTransformError
//		*/
//		public function rotate(file, paramsVar) {
//			return new MediaTransformError('thumbnail_error', 0, 0,
//				get_class(this) . ' rotation not implemented');
//		}
//
//		/**
//		* Returns whether the file needs to be rendered. Returns true if the
//		* file requires rotation and we are able to rotate it.
//		*
//		* @param File file
//		* @return boolean
//		*/
//		public function mustRender(file) {
//			return this.canRotate() && this.getRotation(file) != 0;
//		}
//
//		/**
//		* Check if the file is smaller than the maximum image area for thumbnailing.
//		*
//		* Runs the 'BitmapHandlerCheckImageArea' hook.
//		*
//		* @param File file
//		* @param array paramsVar
//		* @return boolean
//		* @since 1.25
//		*/
//		public function isImageAreaOkForThumbnaling(file, &paramsVar) {
//			global wgMaxImageArea;
//
//			// For historical reasons, hook starts with BitmapHandler
//			checkImageAreaHookResult = null;
//			Hooks::run(
//				'BitmapHandlerCheckImageArea',
//				[ file, &paramsVar, &checkImageAreaHookResult ]
//			);
//
//			if (!is_null(checkImageAreaHookResult)) {
//				// was set by hook, so return that value
//				return (boolean)checkImageAreaHookResult;
//			}
//
//			srcWidth = file.getWidth(paramsVar['page']);
//			srcHeight = file.getHeight(paramsVar['page']);
//
//			if (srcWidth * srcHeight > wgMaxImageArea
//				&& !(file.getMimeType() == 'image/jpeg'
//					&& this.getScalerType(false, false) == 'im')
//			) {
//				// Only ImageMagick can efficiently downsize jpg images without loading
//				// the entire file in memory
//				return false;
//			}
//			return true;
//		}
}
