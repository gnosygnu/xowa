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
package gplx.xowa.mws.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.xowa.mws.filerepo.file.*; import gplx.xowa.mws.media.*;
public class Xomw_parser_env {
	public byte[] Lang__align_end = Bry_.new_a7("right");
	public int User__default__thumbsize = 220;

	public int Global__wgSVGMaxSize = 5120;
	public double Global__wgThumbUpright = .75d;
	public int[] Global__wgThumbLimits = new int[] {120, 150, 180, 200, 250, 300};

	public Xomw_MagicWordMgr Magic_word_mgr() {return magic_word_mgr;} private final    Xomw_MagicWordMgr magic_word_mgr = new Xomw_MagicWordMgr();
	public Xomw_message_mgr Message_mgr() {return message_mgr;} private final    Xomw_message_mgr message_mgr = new Xomw_message_mgr();
	public Xomw_file_finder File_finder() {return file_finder;} private Xomw_file_finder file_finder = new Xomw_file_finder__noop();
	public Xomw_MediaHandlerFactory MediaHandlerFactory() {return mediaHandlerFactory;} private final    Xomw_MediaHandlerFactory mediaHandlerFactory = new Xomw_MediaHandlerFactory();

	public Xomw_parser_env File_finder_(Xomw_file_finder v) {file_finder = v; return this;} 
}
