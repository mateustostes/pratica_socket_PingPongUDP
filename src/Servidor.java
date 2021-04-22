import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	private static List<InetAddress> address = new ArrayList<>();
	private static List<Integer> ports = new ArrayList<>();

	public static void main(String[] args) throws Exception {

		try (DatagramSocket s = new DatagramSocket(4545)) {
			System.out.println("o servidor foi iniciado com sucesso");

			while (true) {
				DatagramPacket obtem = new DatagramPacket(new byte[512], 512);
				s.receive(obtem);
				String obtido = new String(obtem.getData());

				if (obtido.trim().equalsIgnoreCase("start")) {
					address.add(obtem.getAddress());
					ports.add(obtem.getPort());
				} else {
					sendAll(s, obtem);			
					System.out.println(obtido);
				}
			}
		}
	}

	private static void sendAll(DatagramSocket socket, DatagramPacket obtido) throws IOException {
		for (int i = 0; i < address.size(); i++) {
			DatagramPacket resp = new DatagramPacket(obtido.getData(), obtido.getLength(), address.get(i),
					ports.get(i));
			socket.send(resp);
		}
	}
}
