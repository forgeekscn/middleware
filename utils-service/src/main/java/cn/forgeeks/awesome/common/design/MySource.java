package cn.forgeeks.awesome.common.design;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySource implements Serializable {

	private String name;
	private Date createTime;
}
