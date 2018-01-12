package UI;

import static utils.Constantes.TYPE_BEE;
import static utils.Constantes.TYPE_COMPLEXDODGER;
import static utils.Constantes.TYPE_SIMPLEDODGER;
import static utils.Constantes.TYPE_WASP;

import UI.worldsIMPL.WorldBeesWasps;
import UI.worldsIMPL.WorldHappyTreeFriends;
import UI.worldsIMPL.WorldOfDodge;


public class Application
{

	public static void main(String[] args)
	{
		//World world = new WorldBeesWasps("World Bees Wasps");
		World world = new WorldOfDodge("World of dodge");
		//World world = new WorldHappyTreeFriends("World of niggers");
		//world.start(120, 120, false);
		world.start(120, 120, 
				new String[] {
						TYPE_COMPLEXDODGER,
		}, 
				new double[][] {
			{-4.819206566394714,-8.993090859948659,7.266381997600977,-0.982092890263896,4.585797154333383,-9.68802562843883,-10.394852611350457,1.202250917917631,-6.113401275129788,-10.890199838890483,-5.8156558079419405,6.283168969112612,0.6830222774175665,-0.40223723206304224,3.1688903945850138,-3.3673314226416626,-3.5563811177123026,-8.679432160361477,-10.330377280183853,-10.042057211599296,0.2756179928942937,-6.215568876514799,2.335298479857209,-8.874929559029855,9.734065806542251,11.837918260409742,-9.162504199064339,2.9360108718373743,7.737744189560088,4.535338783472497,1.8301592878948947,6.383703961492114,11.267298561008696,10.618812775324333,10.7049927938639,4.4109000058532875,4.260807613362722,7.919179455107899,0.4697368929483234,3.9327624625972604,11.157907216197371,2.8491494877023893,2.9246213441302578,2.685261747280581,-1.6862785595848666,9.532607736532904,11.80856795422619,8.13501007643938,-10.721560395285847,7.9803845369439905,0.8225332370984887,2.4192123441435256,-4.48148933042995,1.5144977824041288,-10.849937874147304,4.106233806976991,-8.946672385382003,-9.081666199282015,2.975664369182783,8.416269613995668,-6.12698327000082,3.3407802798027184,4.9354159784845635,6.301666043151792,8.34070084586968,-6.12698327000082,10.828835565231886,-9.825324150524674,5.241507253236104,-11.02580068012048,-7.429665615876599,-10.285556249817589,-0.25739186071969034,-6.5773612480599475,2.7790219468735082,8.178735064224798,-6.620073512887416,2.8035432384171948,-3.1277370754265403,8.127571054761942,-7.118766412707898,4.176462608024256,0.5997165807367797,7.952107199658713,-1.473509349939642,9.840526120331438,9.040741492459556,5.317653983246959,0.23620248694760387,2.9150680180815054,2.8551622612924628,-2.149114451760715,9.24588655695554,-10.099774515539842,-10.044062220154125,-1.8240366044476335,-11.12866453764266,-10.485254941089256,6.103245888043606,-4.142577905239938,-10.96975324970817,-9.230031009803481,9.564032921298317,8.405636409529345,-3.5351950742642972,-6.84751107980483,-0.3359069134576736,5.086759339832499,-11.232509357987558,-9.067705846810778,-9.693575586080268,0.5520076727858008,4.693120334954321,-3.2363459149467495,8.293318689587311,-10.11893403212923,9.50733754169322,6.415267916992953,8.34070084586968,7.958328621534693,4.6496013843914525,-3.5563811177123026,4.563384900512251,-8.79758591838731,-5.9442743321327995,-7.715234903163719,8.873597943393497,-7.194323167717931,3.209878889511087,-6.73549970504913,-4.881962430034358,4.022356606277185,6.0109839728088295,-9.419309707113719,-11.866305563878981,4.343729890435911,-9.85869755196726,5.400776907490378,12.0,-10.095872194764587,-4.916737848408529,5.313221514649347,1.9088405751374893,-3.38514310306559,0.8362042119905642,-9.171379724620893,-7.14915599330434,5.903753836216912,4.5622095188811125,-4.740136919154834,-8.787654017964533,3.1017324200186778,0.10264594845493702,-6.819066402835584,-3.6231425960031722,-10.77765915599818,2.5560568400531527,-6.113401275129788,-3.254476445078897,-8.29189570207885,5.755539862912258,-0.9885249415757633,-6.9275592607802885,-8.956369120566173,
			},
			});
		
	}

}
