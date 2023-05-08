package team.tjusw.elm.service;

import java.util.List;

import team.tjusw.elm.po.Business;

public interface BusinessService {
	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId);

	public Business getBusinessById(Integer businessId);
}
