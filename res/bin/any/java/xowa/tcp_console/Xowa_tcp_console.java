/*
XOWA TCP console: A TCP console example for XOWA
Copyright (C) 2013 gnosygnu@gmail.com

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
package gplx.xowa;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Xowa_tcp_console {
	public static void main(String[] args) {
		Xowa_tcp_console console = new Xowa_tcp_console();
		console.Run(args);
	}
	private int server_send_port;
	private int server_recv_port;
	private String wiki_domain;
	private int max_length;
	private Xowa_tcp_sender sender;
	public void Run(String[] args) {
        Print_message_line("XOWA TCP client v0.0.0.0");
        
        // parse args
        if (!Parse_args(args)) {
			Print_message_line("XOWA console requires 4 args: server_send_port, server_recv_port, wiki_domain, max_length.");
			Print_message_line("For example, use '55000 55001 simple.wikipedia.org 1000'");
			return;
        }

        // start sender
        sender = new Xowa_tcp_sender(server_send_port);
        
        // start console
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Print_message_line("Enter page name. For example 'Earth'. Press Ctrl+C to exit. Enter '|server_stop' to stop server or '|exit' to exit.");
        Print_message("> ");
        try {
	        while (true) {
	        	String page = Read_string(input);
	        	if 		(page == null)
	        		break;
	        	else if ("|exit".equals(page))
	        		break;
	        	else if ("|server_stop".equals(page)) {
	        		Send_message_server_stop();
	        	}
	        	else
	        		Send_message_fetch_page(page);
	        }
        }
	    catch (Exception e) {
	    	Print_error(e);
	    }
	}
	private void Send_message_fetch_page(String page) {
		Print_message_line("Sending request for " + page);
		// String xowa_msg = "0|0000000128|0000000257|xowa.cmd.exec|id_0|sender_0|recipient_0|2013-07-18 01:23:45.678|app.shell.fetch_page('simple.wikipedia.org/wiki/Earth', 'html');";		
		String command = String.format("app.shell.fetch_page('%s/wiki/%s', 'wiki');", wiki_domain, page);
		Send_message(command);
	}
	private void Send_message_server_stop() {	
		Print_message_line("Sending request to stop server");
		Send_message("app.server.stop;");
	}
	private void Send_message(String command) {
		String id = "id_is_for_client_usage";
		String time = "time_is_for_client_usage";
		String body = String.format("xowa.cmd.exec|%s|xowa_tcp_console|xowa_server|%s|%s", id, time, command);
		int body_len = body.length();
		int cksum = (body_len * 2) + 1;
		String msg = String.format("0|%s|%s|%s", String.format("%010d", body_len), String.format("%010d", cksum), body);

		Xowa_tcp_receiver receiver = new Xowa_tcp_receiver(server_recv_port, max_length);
        new Thread(receiver).start();
    	sender.Send_command(msg);
	}
	private boolean Parse_args(String[] args) {
		if (args.length != 4) {
			Print_message_line("4 arguments must be supplied: " + args.length);
			return false;
		}
		server_send_port = Parse_int(args[0]); 	if (server_send_port == -1) return false;
		server_recv_port = Parse_int(args[1]); 	if (server_recv_port == -1) return false;
		wiki_domain = args[2];
		max_length = Parse_int(args[3]); 		if (max_length == -1) return false;
		return true;
	}
	private static int Parse_int(String raw) {
		try {return Integer.parseInt(raw);}
		catch (Exception e) {
			Print_message_line("argument must be numeric: " + raw);
			return -1;
		}
	}
	private static String Read_string(BufferedReader input) {
        try {return input.readLine();}
        catch (IOException e) {return null;}		
	}
	public static void Print_message_line(String msg) {
        System.out.println(msg);		
	}
	public static void Print_message(String msg) {
        System.out.print(msg);		
	}
	public static void Print_error(Exception e) {
        System.err.println(e.getMessage());		
	}
	public static void Sleep(long millis) {
		try {Thread.sleep(millis);} 
		catch (InterruptedException e) {Print_error(e);}
	}
}
class Xowa_tcp_sender {
	private int port;
	private Socket socket;
	private OutputStream output_stream;
	public Xowa_tcp_sender(int port) {this.port = port;}
	public boolean Open_socket() {
		try {			
			socket = new Socket("localhost", port);
			// socket.setSoTimeout(10000);
			output_stream = socket.getOutputStream();
			return true;
		}
		catch (Exception e) {
			Xowa_tcp_console.Print_error(e);
			return false;
		}
	}
	public void Send_command(String msg) {
		try {
			while (!Open_socket()) {
				Xowa_tcp_console.Sleep(100);
			}
			byte[] buffer = msg.getBytes();
			output_stream.write(buffer, 0, buffer.length);
			Close_socket();
		} 
		catch (Exception e) {
			Xowa_tcp_console.Print_error(e);
		}
	}
	public void Close_socket() {
		try {
			output_stream.close();
			socket.close();
		} 
		catch (Exception e) {
			Xowa_tcp_console.Print_error(e);
		}		
	}
}
class Xowa_tcp_receiver implements Runnable {
	private int port;
	private int max_length;
	private ServerSocket server_socket;
	private Socket client_socket;
	private InputStream input_stream;
	public Xowa_tcp_receiver(int port, int max_length) {
		this.port = port;
		this.max_length = max_length;
	}
	public void run() {
		try {
			// initialization
			server_socket = new ServerSocket(port);
			client_socket = server_socket.accept();
			// client_socket.setSoTimeout(10000);
			byte[] buffer = new byte[65536];
			input_stream = client_socket.getInputStream();
			
			// read incoming messages
			int read = 0;
			while (true) {
				String msg = "";
				// read header
				int body_len_max = 0, body_len_cur = 0;
				read = input_stream.read(buffer, 0, 24);
				if (read == -1) break;
				String body_len_max_str = new String(buffer, 2, 10);
				body_len_max = Integer.parseInt(body_len_max_str);
				buffer = new byte[body_len_max];
				
				// read rest of body
				while (body_len_cur < body_len_max) {
					read = input_stream.read(buffer);
					if (read == -1) break;
					body_len_cur += read;
					msg += new String(buffer, 0, read);
				}
				
				int msg_length = msg.length();
				if (msg_length > max_length) msg_length = max_length;
				Xowa_tcp_console.Print_message_line(msg.substring(0, msg_length));
				Xowa_tcp_console.Print_message("\n\n> ");
			}
			this.Close_socket();
		}
		catch (Exception e) {
			Xowa_tcp_console.Print_error(e);
		}		
	}
	public void Close_socket() {
		try {
			input_stream.close();
			client_socket.close();
			server_socket.close();		
		}
		catch (Exception e) {
//			Xowa_tcp_console.Print_error(e);	// ignore, else error will print in console 
		}		
	}
}
