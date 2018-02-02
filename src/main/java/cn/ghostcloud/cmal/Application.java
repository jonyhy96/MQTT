package cn.ghostcloud.cmal;

import cn.ghostcloud.cmal.service.MqttService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@SpringBootApplication
public class Application implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public HbaseTemplate getHbaseTemplate() {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.rootdir", "hdfs://192.168.31.41:9000/hbase");
		config.set("zookeeper.znode.parent", "/hbase");
		return new HbaseTemplate(config);
	}

    @Autowired
    MqttService mqttService;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			mqttService.connect();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
