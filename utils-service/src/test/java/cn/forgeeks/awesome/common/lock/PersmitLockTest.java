package cn.forgeeks.awesome.common.lock;

import cn.forgeeks.awesome.common.stream.StreamUtil;
import cn.forgeeks.awesome.common.thread.SpringThreadUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class PersmitLockTest {

	@Test
	public void testB() {

		OptmisticLockTest.UserDto userDto =
			new OptmisticLockTest.UserDto(
				UUID.randomUUID().toString(), UUID.randomUUID().toString(), 20, new AtomicInteger(100));

		Integer num = 100;
		PersmitLock lock = new PersmitLock(true);
		List<Runnable> listA =
			StreamUtil.getNatureNumbers(10000).stream()
				.map(k -> new MyRunnableA(num, userDto,lock))
				.collect(Collectors.toList());
		List<Runnable> listB =
			StreamUtil.getNatureNumbers(10000).stream()
				.map(k -> new MyRunnableB(num, userDto,lock))
				.collect(Collectors.toList());

		listA.addAll(listB);

		SpringThreadUtil.submitTask(listA);

		log.info("### {}  {} ", num, JSON.toJSONString(userDto));
	}

}

class MyRunnableA implements Runnable {
	private Integer num;
	private OptmisticLockTest.UserDto userDto;
	private PersmitLock lock;

	public MyRunnableA(Integer num, OptmisticLockTest.UserDto userDto,PersmitLock lock) {
		this.num = num;
		this.userDto = userDto;
		this.lock=lock;
	}

	@Override
	public void run() {
		try {
			this.lock.tryLock();
			this.num++;
			this.userDto.setAge(userDto.getAge() + 1);
		} catch (Exception e) {
		} finally {
			this.lock.unlock();
		}
	}
}

class MyRunnableB implements Runnable {
	private Integer num;
	private OptmisticLockTest.UserDto userDto;
	private PersmitLock lock;

	public MyRunnableB(Integer num, OptmisticLockTest.UserDto userDto,PersmitLock lock) {
		this.num = num;
		this.userDto = userDto;
		this.lock=lock;
	}

	@Override
	public void run() {
		try {
			lock.tryLock();
			this.num--;
			this.userDto.setAge(this.userDto.getAge() - 1);
		}catch (Exception e){
		}finally{
			lock.unlock();
		}

	}
}
