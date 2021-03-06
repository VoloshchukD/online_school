package by.tms.school.controller;

import by.tms.school.model.Category;
import by.tms.school.model.Homework;
import by.tms.school.model.Lesson;
import by.tms.school.model.LessonExamination;
import by.tms.school.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;

@RestController
@RequestMapping("/editor")
@Validated
public class EditorController {

    private final EditorService editorService;

    @Autowired
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @PutMapping(path = "/updCrsName/{courseId}")
    public ResponseEntity<String> updCourseName(@PathVariable("courseId") @Min(1) long courseId,
                                         @RequestParam @NotNull String name){
        return new ResponseEntity(editorService.editCourseName(courseId, name), HttpStatus.OK);
    }

    @PostMapping(path = "/matchCrs&Ctg/{courseId}")
    public ResponseEntity<String> matchCrsCtg(@PathVariable("courseId") @Min(1) long courseId,
                                            @RequestParam @Min(1) long categoryId){
        return new ResponseEntity(editorService.matchCourseAndCategory(courseId, categoryId), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnName/{lessonId}")
    public ResponseEntity<String> updLessonName(@PathVariable("lessonId") @Min(1) long lessonId,
                                            @RequestParam @NotNull String name){
        return new ResponseEntity(editorService.
                editLessonName(lessonId,name), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnContent/{lessonId}")
    public ResponseEntity<String> updLessonContent(@PathVariable("lessonId") @Min(1) long lessonId,
                                                @RequestParam @NotNull String contentFileName){
        return new ResponseEntity(editorService.
                editLessonContent(lessonId,contentFileName), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnLsExam/{lessonId}")
    public ResponseEntity<String> updLessonLsExam(@PathVariable("lessonId") @Min(1) long lessonId,
                                                @RequestBody @NotNull LessonExamination lessonExamination){
        return new ResponseEntity(editorService.editLessonLsExam(lessonId, lessonExamination), HttpStatus.OK);
    }

    @PostMapping(path = "/addlsnexm/{lessonId}")
    public ResponseEntity<String> addLessonExam(@PathVariable("lessonId") @Min(1) long lessonId,
                                                   @RequestBody @NotNull LessonExamination lessonExamination){
        return new ResponseEntity(editorService.addLessonExaminationToLesson(lessonId, lessonExamination)
                , HttpStatus.OK);
    }

    @PostMapping(path = "/addlsnhmwrk/{lessonId}")
    public ResponseEntity<String> addLessonHomework(@PathVariable("lessonId") @Min(1) long lessonId,
                                                    @RequestBody @NotNull Homework homework){
        return new ResponseEntity(editorService.addHomeworkToLesson(lessonId, homework)
                , HttpStatus.OK);
    }

    @PostMapping(path = "/addLsn")
    public ResponseEntity<String> updLessonLsExam(@RequestBody @NotNull Lesson lesson){
        return new ResponseEntity(editorService.addLesson(lesson), HttpStatus.OK);
    }

    @PostMapping(path = "/matchLsn&Crs")
    public ResponseEntity<String> updLessonLsExam( @RequestParam @Min(1) long idlsn, @RequestParam @Min(1) long idcrs){
        return new ResponseEntity(editorService.matchLessonAndCourse(idlsn, idcrs), HttpStatus.OK);
    }

}
