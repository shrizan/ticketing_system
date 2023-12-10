package com.lambton.store.project;

import com.lambton.common.model.*;
import com.lambton.common.store.*;
import com.lambton.model.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectStore<T extends Project> extends Store<T> {

    List<Project> search(long page, long size, Optional<String> optionalTitle);
}
