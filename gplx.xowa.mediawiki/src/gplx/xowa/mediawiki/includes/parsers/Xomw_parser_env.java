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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.filerepo.file.*; import gplx.xowa.mediawiki.includes.media.*;
public class Xomw_parser_env {
	public byte[] Lang__align_end = Bry_.new_a7("right");
	public int User__default__thumbsize = 220;

	public int Global__wgSVGMaxSize = 5120;
	public double Global__wgThumbUpright = .75d;
	public int[] Global__wgThumbLimits = new int[] {120, 150, 180, 200, 250, 300};

	public XomwMagicWordMgr Magic_word_mgr() {return magic_word_mgr;} private final    XomwMagicWordMgr magic_word_mgr = new XomwMagicWordMgr();
	public XomwMessageMgr Message_mgr() {return message_mgr;} private final    XomwMessageMgr message_mgr = new XomwMessageMgr();
	public XomwFileFinder File_finder() {return file_finder;} private XomwFileFinder file_finder = new XomwFileFinderNoop();
	public XomwMediaHandlerFactory MediaHandlerFactory() {return mediaHandlerFactory;} private final    XomwMediaHandlerFactory mediaHandlerFactory = new XomwMediaHandlerFactory();

	public Xomw_parser_env File_finder_(XomwFileFinder v) {file_finder = v; return this;} 
}
