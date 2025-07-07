package com.intimetec.newsportal.exception;

public class EntitySaveException extends NewsPortalException {
  public EntitySaveException(String message) {
    super(message);
  }

  public EntitySaveException(String message, Throwable cause) {
    super(message, cause);
  }
}
