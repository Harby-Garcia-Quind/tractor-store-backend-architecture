CREATE SCHEMA IF NOT EXISTS enrollment;

CREATE TABLE IF NOT EXISTS enrollment.enrollments (
      id UUID PRIMARY KEY,
      user_id UUID NOT NULL,
      course_id UUID NOT NULL,
      status VARCHAR(30) NOT NULL,
      created_at TIMESTAMP NOT NULL,
      CONSTRAINT uk_enrollment_user_course UNIQUE (user_id, course_id)
    );