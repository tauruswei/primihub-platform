package com.primihub.biz.repository.primarydb.abe;

import com.primihub.biz.entity.abe.po.AbeProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbeProjectRepository {
    Long saveProject(AbeProject abeProject);
    List<AbeProject> listProjects();
    AbeProject queryProject(Long id);
    List<AbeProject> listProjectsByName(String name);
    Long deleteProject(Long id);
    Long updateProject(AbeProject abeProject);
}
