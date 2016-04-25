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
import gplx.core.envs.*; import gplx.core.threads.*;
class Io_zip_decompress_cmd__jre implements Io_zip_decompress_cmd {
	public void Decompress__exec(Io_zip_decompress_task task, Io_url src_fil, Io_url trg_dir) {
				// init
		byte[] buffer = new byte[4096];
		String resume_entry_name = task.Resume__entry();
		long resume_bytes = task.Resume__bytes();
		
		// assert that trg_dir exists and can be written
		Io_mgr.Instance.CreateDirIfAbsent(trg_dir);
		
		// open src_fil_stream
		FileInputStream src_fil_stream = null;
		try 	{src_fil_stream = new FileInputStream(src_fil.Raw());}
		catch 	(FileNotFoundException e) {throw Err_.new_("ios.zip", "file not found", "path", src_fil.Raw());}
		
		ZipInputStream src_zip_stream = new ZipInputStream(src_fil_stream);
		try {
			ZipEntry entry = null;
			boolean loop_entries = true;
			while (loop_entries) {
				
				// read next entry
				entry = src_zip_stream.getNextEntry();				
				if (	entry == null									// no more entries
					||	resume_entry_name == null						// resume_entry_name will be null in most cases 
					||	String_.Eq(resume_entry_name, entry.getName())	// if resume_entry_name is not null, keep reading until match
					)
					break;
				if (resume_bytes > 0) {
					src_zip_stream.skip(resume_bytes);
					resume_bytes = 0;
				}
				
				// get entry name; also convert / to \ for wnt
				String entry_name = entry.getName();
				task.Prog__update_name(entry_name);
				if (Op_sys.Cur().Tid_is_wnt()) entry_name = String_.Replace(entry_name, "/", "\\");
				
				// create file
				Io_url trg_fil_url = Io_url_.new_any_(trg_dir.GenSubFil(entry_name).Raw());
				Io_mgr.Instance.CreateDirIfAbsent(trg_fil_url.OwnerDir());	// make sure owner dir exists
				if (trg_fil_url.Type_fil()) {
					// write file
					Io_mgr.Instance.SaveFilStr_args(trg_fil_url, "").Exec();	// need to write to create dirs; permissions;
					FileOutputStream trg_fil_stream = new FileOutputStream(new File(trg_fil_url.Raw()));
					boolean loop_bytes = true;
					while (loop_bytes) {
						int len = src_zip_stream.read(buffer); if (len < 1) break;
						trg_fil_stream.write(buffer, 0, len);
						switch (task.Prog__update(len)) {
							case Io_zip_decompress_task.Status__canceled:
								loop_entries = false;
								loop_bytes = false;
								break;
							case Io_zip_decompress_task.Status__paused:
								while (true) {
									Thread_adp_.Sleep(1000);
									if (!task.Paused()) break;
									if (task.Canceled()) {
										loop_entries = false;
										loop_bytes = false;
										break;
									}
								}
								break;
							case Io_zip_decompress_task.Status__ok:
								break;
						}
					}
					trg_fil_stream.close();	 
				}
			}
		}
		catch(IOException e) {throw Err_.new_exc(e, "ios.zip", "error duing unzip", "src", src_fil.Raw(), "trg", trg_dir.Raw());}
		finally {
			try {
				src_zip_stream.closeEntry();
				src_zip_stream.close();
			} catch (Exception e) {}
		}
			}
}
