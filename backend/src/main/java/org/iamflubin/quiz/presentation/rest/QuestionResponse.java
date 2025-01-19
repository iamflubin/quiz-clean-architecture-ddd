package org.iamflubin.quiz.presentation.rest;

import java.util.Set;
import java.util.UUID;


record QuestionResponse(UUID id, String text, Set<String> answers) {
}

