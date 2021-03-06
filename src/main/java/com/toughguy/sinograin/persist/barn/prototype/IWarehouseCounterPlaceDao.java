package com.toughguy.sinograin.persist.barn.prototype;

import java.util.List;

import com.toughguy.sinograin.model.barn.WarehouseCounterPlace;
import com.toughguy.sinograin.persist.prototype.IGenericDao;

public interface IWarehouseCounterPlaceDao extends IGenericDao<WarehouseCounterPlace, Integer>{
	
	/**
	 * 根据placeId查询存放位置
	 * @param placeId
	 * @return
	 */
	public WarehouseCounterPlace findDepotAndCounterByPlaceId(int placeId);
	/**
	 * 根据counterId查询存放位置
	 * @param counterId
	 * @return
	 */
	public List<WarehouseCounterPlace> findPlaces(int counterId);
	/**
	 * 根据counter查询存放位置
	 * @param counterId
	 * @return
	 */
	public List<WarehouseCounterPlace> findPlacesByCounter(String counter);
}
