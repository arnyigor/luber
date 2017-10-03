package com.arny.lubereckiy.api;

public class API {
	/*
		https://api.pik.ru/v1/block?new_design=1&types=1,2&metadata=1&statistics=1//все объекты
		https://www.pik.ru/luberecky/datapages?data=FlatPlans//план этажей
		https://www.pik.ru/luberecky/datapages?data=GenPlan//общий генплан
		https://api.pik.ru/v1/flat?flat_id=CA72B0A1-03AE-E611-9FBE-001EC9D5643C//квартира
		https://www.pik.ru/luberecky/singlepage?data=ChessPlan&id=293ecc9b-bfad-e611-9fbe-001ec9d5643c&private_key=1&format=json&domain=www.pik.ru//корпус
		&locations=8,32//округа и города
		&price_million=1// цена в млн
		&price_from=4//цена от
		&price_to=5//цена до
		&settlement=2.2018//кварталы 1-4
		*/
	public static final String BASE_API_URL = "https://api.pik.ru/v1/";
	public static final String BASE_URL = "https://www.pik.ru/";
	/**
	 * Все объекты М и МО (2,3)
	 * metadata - данные
	 */
	public static final String API_ALL_OBJECTS = "block?new_design=1&types=1,2&metadata=1&statistics=1&locations=2,3&images=1";
	/**
	 * План этажей
	 */
	public static final String API_FLATPLANS ="{object}/datapages?data=FlatPlans";
	public static final String API_GENPLAN = "{object}/datapages?data=GenPlan";
	/**
	 * Квартира
	 */
	public static final String API_SINGLE_FLAT ="flat?flat_id={flatid}";
	/**
	 * Корпус
	 */
	public static final String API_SINGLE_CORPUS ="{object}/singlepage?data=ChessPlan&format=json&domain=pik.ru";

}
