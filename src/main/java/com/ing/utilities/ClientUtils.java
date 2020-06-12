package com.ing.utilities;

import org.springframework.util.StringUtils;

import com.ing.entities.Client;

public class ClientUtils {

	private ClientUtils() {

	}

	/**
	 * Checks that all the client attributes are not null or empty
	 * 
	 * @param client
	 * @return boolean
	 */
	public static boolean isValidClient(Client client) {
		boolean isValid = (!StringUtils.isEmpty(client.getIdentityDocNumber())
				&& !StringUtils.isEmpty(client.getFirstName()) && !StringUtils.isEmpty(client.getLastName())
				&& !StringUtils.isEmpty(client.getNationality()) && !StringUtils.isEmpty(client.getAddress())
				&& !StringUtils.isEmpty(client.getEmail()) && !StringUtils.isEmpty(client.getPhoneNumber()));

		return isValid;
	}
}
