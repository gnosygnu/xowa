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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import java.io.*;
import java.util.zip.*;
import gplx.core.envs.*; import gplx.core.threads.*; import gplx.core.progs.*;
class Io_zip_decompress_cmd__jre extends Io_zip_decompress_cmd__base {
	@Override public Io_zip_decompress_cmd Make_new() {return new Io_zip_decompress_cmd__jre();}
	@Override protected byte Exec_hook(gplx.core.progs.Gfo_prog_ui prog_ui, Io_url src_fil, Io_url trg_dir, List_adp trg_fils, String resume_name, long resume_file, long resume_item) {
				// open src_zip_stream
		FileInputStream src_fil_stream = null;
		try 	{src_fil_stream = new FileInputStream(src_fil.Raw());}
		catch 	(FileNotFoundException e) {throw Err_.new_("ios.zip", "file not found", "path", src_fil.Raw());}
		ZipInputStream src_zip_stream = new ZipInputStream(src_fil_stream);	

		// init variables for entry loop
		boolean resumed = resume_name != null;
		long file_cur_in_raw = resumed ? resume_file : 0;
		long file_max_in_raw = prog_ui.Prog_data_end();
		ZipEntry entry = null;
		byte[] buffer = new byte[4096];		
		Io_mgr.Instance.CreateDirIfAbsent(trg_dir); // NOTE: assert that trg_dir exists
		
		try {
			while (true) {	// loop over entries
				entry = src_zip_stream.getNextEntry();
				if (entry == null) break;							// no more entries
				if (resume_name != null) {							// resume_entry_name will be null in most cases
					if (String_.Eq(resume_name, entry.getName()))	// if resume_entry_name is not null, keep reading until match
						resume_name = null;
					else
						continue;
				}
				
				// get entry name; also convert / to \ for wnt
				String entry_name = entry.getName();
				if (Op_sys.Cur().Tid_is_wnt()) entry_name = String_.Replace(entry_name, "/", "\\");
				
				// create file
				Io_url trg_fil_url = Io_url_.new_any_(trg_dir.GenSubFil(entry_name).Raw());
				Io_url trg_tmp_url = trg_fil_url.GenNewNameAndExt(trg_fil_url.NameAndExt() + ".tmp");
				if (trg_tmp_url.Type_fil()) {
					// handle resume
					long item_in_raw = 0;					
					if (resume_item > 0) {
						src_zip_stream.skip(resume_item);
						Io_mgr.Instance.Truncate_fil(trg_tmp_url, resume_item);
						item_in_raw = resume_item;
						resume_item = 0;
					}
					FileOutputStream trg_fil_stream = new FileOutputStream(new File(trg_tmp_url.Raw()), resumed);
					if (resumed) resumed = false;
					boolean loop = true;
					while (loop) {	// loop over bytes
						int read_in_raw = src_zip_stream.read(buffer); if (read_in_raw < 1) break;
						trg_fil_stream.write(buffer, 0, read_in_raw);
						item_in_raw += read_in_raw;
						file_cur_in_raw += read_in_raw;
						Checkpoint__save(entry_name, file_cur_in_raw, item_in_raw);
						if (prog_ui.Prog_notify_and_chk_if_suspended(file_cur_in_raw, file_max_in_raw)) {
							loop = false;
							break;
						}
					}
					trg_fil_stream.close();
					if (!loop) return Gfo_prog_ui_.Status__suspended;	// manually canceled
				}
				Io_mgr.Instance.MoveFil_args(trg_tmp_url, trg_fil_url, true).Exec();
				trg_fils.Add(trg_fil_url);
			}
			Gfo_evt_mgr_.Pub_val(Io_mgr.Instance, Io_mgr.Evt__fil_created, trg_fils.To_ary(Io_url.class));
		}
		catch(IOException e) {throw Err_.new_exc(e, "ios.zip", "error duing unzip", "src", src_fil.Raw(), "trg", trg_dir.Raw());}
		finally {
			try {
				// src_zip_stream.closeEntry(); // TOMBSTONE: takes a long time to close; does not seem to be necessary
				src_zip_stream.close();
			} catch (Exception e) {}
		}
		return Gfo_prog_ui_.Status__done;
			}
}
