package neural_network;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import genetics.Individu;


public class NeuralNetwork extends Individu
{

	private Neuron[] inputNeurons;
	private Neuron[] hiddenNeurons;
	private Neuron[] outputNeurons;
	private Connection[] connections;


	public NeuralNetwork(String name, int inputCount, int hiddenCount, int outputCount)
	{
		super(name);
		init(inputCount,hiddenCount,outputCount);
		connections = new Connection[inputCount*hiddenCount+hiddenCount*outputCount];
		for (int i=0;i<connections.length;i++)
			connections[i] = new Connection();
	}

	private NeuralNetwork(NeuralNetwork nn)
	{
		super(nn.name);
		init(nn.inputNeurons.length,nn.hiddenNeurons.length,nn.outputNeurons.length);
		connections = new Connection[nn.connections.length];
		for (int i=0;i<connections.length;i++)
			connections[i] = nn.connections[i].deepCopy();
	}

	private void init(int inputCount, int hiddenCount, int outputCount)
	{
		inputNeurons  = new Neuron[inputCount];
		hiddenNeurons = new Neuron[hiddenCount];
		outputNeurons = new Neuron[outputCount];
		for (int i=0;i<inputNeurons.length;i++)
			inputNeurons[i] = new Neuron();
		for (int i=0;i<hiddenNeurons.length;i++)
			hiddenNeurons[i] = new Neuron();
		for (int i=0;i<outputNeurons.length;i++)
			outputNeurons[i] = new Neuron();
	}

	public double[] getOutputs(double[] inputs)
	{
		double[] outputs = new double[outputNeurons.length];
		if (inputs.length!=inputNeurons.length)
			return null;

		for (int i=0;i<hiddenNeurons.length;i++)
			hiddenNeurons[i].resetSum();
		for (int i=0;i<outputNeurons.length;i++)
			outputNeurons[i].resetSum();

		for (int i=0;i<inputs.length;i++)
			inputNeurons[i].setValue(inputs[i]);

		for (int i=0;i<inputNeurons.length;i++)
		{
			double value = inputNeurons[i].getValue();
			for (int j=0;j<hiddenNeurons.length;j++)
			{
				double weight= connections[i*hiddenNeurons.length+j].getWeight();
				hiddenNeurons[j].addToSum(value, weight);
			}
		}
		for (Neuron n : hiddenNeurons)
			n.applyActivationFunction();

		for (int i=0;i<hiddenNeurons.length;i++)
		{
			double value = hiddenNeurons[i].getValue();
			for (int j=0;j<outputNeurons.length;j++)
			{
				double weight= connections[inputNeurons.length*hiddenNeurons.length+i*outputNeurons.length+j].getWeight();
				outputNeurons[j].addToSum(value, weight);
			}
		}

		for (int i=0;i<outputNeurons.length;i++)
		{
			outputNeurons[i].applyActivationFunction();
			outputs[i]=outputNeurons[i].getValue();
		}

		return outputs;
	}

	public void printNetwork()
	{
		for (int i=0;i<inputNeurons.length;i++)
			for (int j=0;j<hiddenNeurons.length;j++)
				System.out.println("INPUTNEURON("+i+")    \tvers HIDDENNEURON("+j+")\t; POIDS : "+connections[i*hiddenNeurons.length+j].getWeight());
		for (int i=0;i<hiddenNeurons.length;i++)
			for (int j=0;j<outputNeurons.length;j++)
				System.out.println("HIDDENNEURON("+i+")    \tvers OUTPUTNEURON("+j+")\t; POIDS : "+connections[inputNeurons.length*hiddenNeurons.length + i*outputNeurons.length+j].getWeight());
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
		connections[new Random().nextInt(connections.length)].mutate();
	}

	public String toString()
	{
		String str="["+name+"] : ";
		for (int i=0;i<inputNeurons.length;i++)
			for (int j=0;j<hiddenNeurons.length;j++)
				str+=connections[i*hiddenNeurons.length+j].getWeight()+",";
		for (int i=0;i<hiddenNeurons.length;i++)
			for (int j=0;j<outputNeurons.length;j++)
				str+=connections[inputNeurons.length*hiddenNeurons.length + i*outputNeurons.length+j].getWeight()+",";
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

		int maxLength = Math.max(inputNeurons.length, Math.max(hiddenNeurons.length, outputNeurons.length));
		int neuronSz = szRect/maxLength-szRect/(maxLength*5);


		g.setColor(myColour);
		g.fillRect(xRect, yRect, szRect, szRect*9/maxLength);

		for (int i=0;i<inputNeurons.length;i++)
		{
			double value = inputNeurons[i].getValue();
			Color c = new Color((int)(255-value*255),(int)(255-value*255),(int)(255-value*255),alpha);
			int xNeuron1 = xRect+szRect/(maxLength*10)+i*szRect/maxLength;
			int yNeuron1 = yRect+szRect/maxLength;

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

		for (int i=0;i<hiddenNeurons.length;i++)
		{
			double value = hiddenNeurons[i].getValue();
			Color c = new Color((int)(255-value*255),(int)(255-value*255),(int)(255-value*255),alpha);
			int xNeuron1 = xRect+szRect/(maxLength*10)+i*szRect/maxLength;
			int yNeuron1 = yRect+szRect*4/maxLength;

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

		for (int i=0;i<outputNeurons.length;i++)
		{
			double value = outputNeurons[i].getValue();
			Color c = new Color((int)(255-value*255),(int)(255-value*255),(int)(255-value*255),alpha);
			int xNeuron1 = xRect+szRect/(maxLength*10)+i*szRect/maxLength;
			int yNeuron1 = yRect+szRect*7/maxLength;
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

		g.setColor(oldColor);
	}


}
