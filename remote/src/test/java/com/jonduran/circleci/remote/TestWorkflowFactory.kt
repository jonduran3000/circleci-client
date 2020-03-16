package com.jonduran.circleci.remote

import com.jonduran.circleci.remote.model.Workflow
import java.util.UUID

object TestWorkflowFactory {
    @JvmStatic fun create(): Workflow {
        val jobId = UUID.randomUUID()
        val workflowId = UUID.randomUUID()
        return Workflow(
            jobId = jobId,
            jobName = "test_job",
            workflowId = workflowId,
            workflowName = "test_workflow"
        )
    }
}