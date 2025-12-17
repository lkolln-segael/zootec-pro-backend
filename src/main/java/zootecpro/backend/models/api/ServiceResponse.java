package zootecpro.backend.models.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
  T data;
  ServiceStatus status;
  String message;

  public static ServiceResponse<?> empty(ServiceStatus status, String message) {
    return ServiceResponse.builder()
        .status(status)
        .message(message)
        .build();
  }
}
