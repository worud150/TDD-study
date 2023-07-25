package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoInsParam;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.model.TodoVo;
import com.green.todotestapp.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    private  final  TodoMapper MAPPER;
    private final String FILE_DIR;

    @Autowired
    public TodoServiceImpl(TodoMapper mapper, @Value("${file.dir}") String fileDir) {
        this.MAPPER = mapper;
        this.FILE_DIR = MyFileUtils.getAbsolutePath(fileDir);
    }

    @Override
    public List<TodoVo> selTodo() {
        return MAPPER.selTodo();
    }

    @Override
    public TodoRes insTodo(TodoInsParam param) {

        File tempDir = new File(FILE_DIR, "/temp");

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // 저장용 파일명
        String savedFileNm = MyFileUtils.makeRandomFileNm(param.getPic().getOriginalFilename());

        File tempFile = new File(tempDir.getPath(), savedFileNm);

        try {
            param.getPic().transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt(param.getCtnt());
        dto.setPic(savedFileNm);
        int result = MAPPER.insTodo(dto);
        if (result == 1){
            // 파일 이동
            String targetDicPath = FILE_DIR + "/todo/" + dto.getItodo();
            File targetDic = new File(targetDicPath);
            if (!targetDic.exists()) {
                targetDic.mkdirs();
            }
            File targetFile = new File(targetDicPath, savedFileNm);
            tempFile.renameTo(targetFile);
            return  new TodoRes(dto);
        }
        return null;
    }

}
