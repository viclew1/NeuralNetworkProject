package neural_network;

public class App
{

	public static void main(String[] args) throws InterruptedException
	{
		NeuralNetwork nn = new NeuralNetwork("test",2, 2, 2);
		nn.printNetwork();
	}

}
