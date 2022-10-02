package com.asstnavi.generic.Interface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Map;

public interface IGetCommand
{
    @GetMapping
    <T> T getFirst(@PathVariable int employeeRecordId, @PathVariable String wsKey);

    @GetMapping
    <T> T getFirst(@PathVariable Map<String, String> whereCommand, @PathVariable String wsKey);

    @GetMapping
    <T> T getFirst(@PathVariable String whereCommand, @PathVariable String wsKey);

    @GetMapping
    <T> ArrayList<T> getList(@PathVariable String wsKey);

    @GetMapping
    <T> ArrayList<T> getList(@PathVariable String whereCommand, @PathVariable String wsKey);
}
