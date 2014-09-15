package org.transmartproject.batch.tasklet

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter

import java.sql.PreparedStatement
import java.sql.SQLException

/**
 *
 */
abstract class GenericTableUpdateTasklet implements Tasklet, PreparedStatementSetter {

    @Autowired
    private JdbcTemplate jdbcTemplate

    @Value("#{jobParameters['studyId']}")
    String studyId

    @Override
    final RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        int count = jdbcTemplate.update(sql(), this)
        contribution.incrementWriteCount(count)
        println contribution
        RepeatStatus.FINISHED
    }

    abstract String sql()

}