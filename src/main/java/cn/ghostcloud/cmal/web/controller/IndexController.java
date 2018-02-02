package cn.ghostcloud.cmal.web.controller;

import cn.ghostcloud.cmal.enums.CallbackCode;
import cn.ghostcloud.cmal.enums.DataTypeEnum;
import cn.ghostcloud.cmal.service.ProducerService;
import cn.ghostcloud.cmal.utils.SignatureUtils;
import cn.ghostcloud.cmal.web.DataHeader;
import cn.ghostcloud.cmal.web.model.RangeEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Autowired
    ProducerService producerService;

    @GetMapping("/data")
    public DataHeader getData() {
        RangeEventData rangeEventData = new RangeEventData.Builder().setCarnum("ewefdafasdf").build();
        DataHeader<RangeEventData> testDataHeader = new DataHeader<>(DataTypeEnum.rangeEvent.name(), rangeEventData);
        hbaseTemplate.put("test", "row1", "cf", "1", "adfad".getBytes());
        return testDataHeader;
    }

    @PostMapping("${push.http.callback-url}")
    public ResponseEntity postData(RequestEntity request) {
        Boolean isSignatureValid = SignatureUtils.isSignatureValid(request);
        // 签名校验
//        if (!isSignatureValid) return ResponseEntity.badRequest().body(CallbackCodeDecoder.decode(CallbackCode.INVALID_SIGNATURE));
        CallbackCode result = producerService.process(request.getBody());
        return ResponseEntity.ok(result.result());
    }
}
