package com.lambton.utility;

import com.lambton.common.model.*;
import com.lambton.model.project.*;

import java.util.*;

public interface FileUtility<T extends BaseModel> {
    void initFiles();

    void deleteFile();

    String getFilePath();

    Map<String, T> readAllEntities();

    boolean writeToFile(Map<String, T> entities);
}
