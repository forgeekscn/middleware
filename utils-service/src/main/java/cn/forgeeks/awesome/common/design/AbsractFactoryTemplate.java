package cn.forgeeks.awesome.common.design;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbsractFactoryTemplate {

	private static CarFactory autoCarfactory=CarFactory.getFactory("auto");
	private static CarFactory teslaCarfactory=CarFactory.getFactory("tesla");
	private static CarFactory toyotaCarfactory=CarFactory.getFactory("toyota");

	public static Car getCar(String type){
		if(type.equals("auto")){
			return autoCarfactory.getCar();
		}else if(type.equals("tesla")){
			return teslaCarfactory.getCar();
		}else{
			return toyotaCarfactory.getCar();
		}
	}

}




interface Car{
	public void run();
}

@Slf4j
class TeslaCar implements Car{

	@Override
	public void run() {

		log.info("### tesla run");

	}
}


@Slf4j
class AutoCar implements  Car{

	@Override
	public void run() {
		log.info("### auto run");
	}

}

@Slf4j
class ToyotaCar implements  Car{

	@Override
	public void run() {
		log.info("### toyota run");
	}

}



abstract class CarFactory{
	abstract Car getCar();
	public static CarFactory getFactory(String type){
		if(type.equals("auto")){
			return new AutoCarFactory();
		}else if(type.equals("tesla")){
			return new TeslaCarFactory();
		}else{
			return new ToyotaCarFactory();
		}
	}
}


@Slf4j
class TeslaCarFactory extends  CarFactory {

	@Override
	public Car getCar() {
		log.info("###  new tesla car");
		return new TeslaCar();
	}
}



@Slf4j
class AutoCarFactory extends CarFactory{
	@Override
	public Car getCar() {
		log.info("### new auto car");
		return new AutoCar();
	}
}


@Slf4j
class ToyotaCarFactory extends CarFactory{
	@Override
	public Car getCar() {
		log.info("### new toyota car");
		return new ToyotaCar();
	}
}









