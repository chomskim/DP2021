package hufs.ces.chat;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MQChatController {
	
	private final static String host = "220.67.121.119";
	private final static String vhost = "dp2018.chat";
	private final static String user = "dp2018";
	private final static String pass = "dp2018@chat";
	private String[] chatRooms = {"chat.strategy", "chat.observer", "chat.singleton", "chat.factory"};
	private String connExchange = chatRooms[0];
	
	ConnectionFactory factory = null;
	Connection connection = null;
	Channel channel = null;
	
	@FXML
	private void initialize() {
		factory = new ConnectionFactory();
		factory.setHost(host);
	    factory.setVirtualHost(vhost);
		factory.setUsername(user);
		factory.setPassword(pass);

	}
	@FXML
    private MenuItem rmStrategy;

    @FXML
    private MenuItem rmObserver;

    @FXML
    private MenuItem rmSingleton;

    @FXML
    private MenuItem rmFactory;

    @FXML
    private Button connBtn;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private Button btnExit;

    @FXML
    void handleConnect(ActionEvent event) {
    	try {
			connection = factory.newConnection();
	    	channel = connection.createChannel();
	    	channel.exchangeDeclare(connExchange, "fanout");
	    	String queueName = channel.queueDeclare().getQueue();
	    	channel.queueBind(queueName, connExchange, "");
			boolean autoAck = true;
			channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope	envelope, AMQP.BasicProperties properties, byte[] body) 
						throws IOException {
					String msg = new String(body);
					textArea.setText(textArea.getText()+">>" + msg + "\n");
				}
			});

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void handleExit(ActionEvent event) {
    	try {
			channel.close();
	    	connection.close();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
    	System.exit(0);
    }

    @FXML
    void handleRmFactory(ActionEvent event) {
    	connExchange = chatRooms[3];
    }

    @FXML
    void handleRmObserver(ActionEvent event) {
    	connExchange = chatRooms[1];
    }

    @FXML
    void handleRmSingleton(ActionEvent event) {
    	connExchange = chatRooms[2];
    }

    @FXML
    void handleRmStrategy(ActionEvent event) {
    	connExchange = chatRooms[0];
    }

    @FXML
    void handleTextField(ActionEvent event) {
    	try {
    		String msg = textField.getText();
			textArea.setText(textArea.getText()+"<<" + msg + "\n");
			channel.basicPublish(connExchange, "", null, msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
