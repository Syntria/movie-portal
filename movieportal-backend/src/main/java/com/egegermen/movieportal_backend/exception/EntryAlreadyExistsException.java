package com.egegermen.movieportal_backend.exception;

public class EntryAlreadyExistsException extends RuntimeException {

	public EntryAlreadyExistsException(String itemType, String listType) {

		super(String.format("%s already exists in the user's %s.", itemType, listType));
	}
}
