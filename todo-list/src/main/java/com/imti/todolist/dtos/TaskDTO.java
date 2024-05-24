package com.imti.todolist.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class TaskDTO {
	private Long id;
	private String description;
	private boolean completed;
	private Date createdAt;
}
