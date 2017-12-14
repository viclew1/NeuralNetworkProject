package neural_network;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import genetics.Individu;


public class NeuralNetwork extends Individu
{

	private int[] layersSizes;
	private NeuralLayer[] layers;
	private Connection[] connections;


	public NeuralNetwork(String name, int[] layersSizes)
	{
		super(name);
		this.layersSizes=layersSizes;
		initLayers();
		int connectionsSz = 0 ;
		for (int i = 0 ; i < layersSizes.length - 1 ; i++)
			connectionsSz += layersSizes[i] * layersSizes[i+1];

		connections = new Connection[connectionsSz];
		for (int i=0;i<connections.length;i++)
			connections[i] = new Connection();
	}

	private NeuralNetwork(NeuralNetwork nn)
	{
		super(nn.name);
		this.layersSizes=nn.layersSizes;
		initLayers();
		connections = new Connection[nn.connections.length];
		for (int i=0;i<connections.length;i++)
			connections[i] = nn.connections[i].deepCopy();
	}

	private void initLayers()
	{
		layers = new NeuralLayer[layersSizes.length];
		for (int i = 0 ; i < layersSizes.length ; i ++)
			layers[i] = new NeuralLayer(layersSizes[i]);
	}

	public double[] getOutputs(double[] inputs)
	{
		int connectionCpt = 0;

		for (NeuralLayer nl : layers)
			nl.resetSums();

		Neuron[] inputNeurons = layers[0].getNeurons();
		for (int i = 0 ; i < inputNeurons.length ; i++)
			inputNeurons[i].setValue(inputs[i]);

		for (int i = 0 ; i < layersSizes.length - 1 ; i++)
		{
			Neuron[] from = layers[i].getNeurons();
			Neuron[] to   = layers[i+1].getNeurons();
			for (int j = 0 ; j < from.length ; j++)
			{
				double value = from[j].getValue();
				for (int k = 0 ; k < to.length ; k++)
				{
					double weight = connections[connectionCpt++].getWeight();
					to[k].addToSum(value, weight);
				}
			}
			layers[i+1].applyActivationFunctions();
		}

		return layers[layers.length-1].getValues();
	}

	public NeuralNetwork deepCopy()
	{
		return new NeuralNetwork(this);
	}

	@Override
	public boolean equals(Object o)
	{
		NeuralNetwork nn = (NeuralNetwork)o;
		if (nn.connections.length!=connections.length)
			return false;
		for (int i=0;i<connections.length;i++)
			if (connections[i].getWeight()!=nn.connections[i].getWeight())
				return false;
		return true;
	}

	@Override
	public void crossOver(Individu individu)
	{
		NeuralNetwork nn = (NeuralNetwork) individu;
		if (nn.connections.length!=connections.length)
			return;
		int index = new Random().nextInt(connections.length);
		if (new Random().nextBoolean())
			for (int i=index;i<connections.length;i++)
				connections[i]=nn.connections[i].deepCopy();
		else
			for (int i=0;i<index;i++)
				connections[i]=nn.connections[i].deepCopy();
	}

	@Override
	public void mutate()
	{
		int mutationCount = Math.max(1, connections.length/50);
		for (int i = 0 ; i < mutationCount ; i++)
			connections[new Random().nextInt(connections.length)].mutate();
	}

	public String toString()
	{
		String str="["+name+"] : ";
		for (Connection c : connections)
			str+=c.getWeight()+",";
		return str;
	}

	@Override
	public void draw(Graphics g)
	{
		Color oldColor = g.getColor();
		int xRect = 150;
		int yRect = 10;
		int szRect = 700;

		int alpha = 200;
		Color myColour = new Color(255, 255, 255, alpha);

		int maxLength = 0;
		for (NeuralLayer nl : layers)
		{
			maxLength = Math.max(maxLength, nl.getNeurons().length);
		}

		int neuronSz = szRect/maxLength-szRect/(maxLength*5);


		g.setColor(myColour);
		g.fillRect(xRect, yRect, szRect, szRect*9/maxLength);

		for (int layer = 0 ; layer < layers.length ; layer ++)
		{
			Neuron[] neurons = layers[layer].getNeurons();
			for (int i=0;i<neurons.length;i++)
			{
				double value = neurons[i].getValue();
				Color c = new Color((int)(255-value*255),(int)(255-value*255),(int)(255-value*255),alpha);
				int xNeuron1 = xRect+szRect/(maxLength*10)+i*szRect/maxLength;
				int yNeuron1 = yRect+2*layer*szRect/maxLength;

				g.setColor(c);
				g.fillOval(xNeuron1,
						yNeuron1,
						neuronSz,
						neuronSz);
				g.setColor(new Color(0,0,0,alpha));
				g.drawOval(xNeuron1,
						yNeuron1,
						neuronSz,
						neuronSz);
			}
		}

		g.setColor(oldColor);
	}
}
