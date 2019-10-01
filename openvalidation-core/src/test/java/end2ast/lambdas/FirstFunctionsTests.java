/*
 *    Copyright 2019 BROCKHAUS AG
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package end2ast.lambdas;

import end2ast.End2AstRunner;
import io.openvalidation.common.ast.ASTComparisonOperator;
import io.openvalidation.common.data.DataPropertyType;
import org.junit.jupiter.api.Test;

public class FirstFunctionsTests {

  /*
  todo evtl first 10 items -->OF
  todo sorting arrays

  die ersten 10 items/streets
  take 10 items from addresses
  take 10 street from addresses
  take 10 items from addresses with city gleich dortmund
  take 10 street from addresses with city gleich dortmund




   */

  @Test
  public void first_function_simple() throws Exception {
    String rule = "a first item from addresses as a first address";
    String schema = "{addresses:[]}";

    End2AstRunner.run(
        rule,
        schema,
        r ->
            r.variables()
                .hasSizeOf(1)
                .first()
                .hasName("a first address")
                .operandFunction()
                .hasName("FIRST")
                .hasType(DataPropertyType.Object)
                .sizeOfParameters(1)
                .parameters()
                .first()
                .property("addresses")
                .hasType(DataPropertyType.Array));
  }

  @Test
  public void first_function_with_simple_condition() throws Exception {
    String rule = "a first item from addresses with zip_code equals 12345 as a first address";
    String schema = "{addresses:[{zip_code: 1, city: Berlin}]}";

    End2AstRunner.run(
        rule,
        schema,
        r ->
            r.variables()
                .hasSizeOf(1)
                .first()
                .hasName("a first address")
                .operandFunction()
                .hasName("FIRST")
                .hasType(DataPropertyType.Object)
                .sizeOfParameters(1)
                .parameters()
                .first()
                .function()
                .hasName("WHERE")
                .sizeOfParameters(2)
                .parameters()
                .first()
                .property("addresses")
                .hasType(DataPropertyType.Array)
                .parentList()
                .second()
                .lambdaCondition()
                .hasOperator(ASTComparisonOperator.EQUALS)
                .hasNoConnector()
                .leftProperty("zip_code")
                .hasType(DataPropertyType.Decimal)
                .parentCondition()
                .rightNumber(12345.0));
  }

  @Test
  public void first_function_with_simple_condition_and_sugar_around_array_and_property()
      throws Exception {
    String rule =
        "The first item from the addresses given with a zip_code number greater than 12345 as a first address";
    String schema = "{addresses:[{zip_code: 1, city: Berlin}]}";

    End2AstRunner.run(
        rule,
        schema,
        r ->
            r.variables()
                .hasSizeOf(1)
                .first()
                .hasName("a first address")
                .operandFunction()
                .hasName("FIRST")
                .hasType(DataPropertyType.Object)
                .sizeOfParameters(1)
                .parameters()
                .first()
                .function()
                .hasName("WHERE")
                .sizeOfParameters(2)
                .parameters()
                .first()
                .property("addresses")
                .hasType(DataPropertyType.Array)
                .parentList()
                .second()
                .lambdaCondition()
                .hasOperator(ASTComparisonOperator.GREATER_THAN)
                .hasNoConnector()
                .leftProperty("zip_code")
                .hasType(DataPropertyType.Decimal)
                .parentCondition()
                .rightNumber(12345.0));
  }

  @Test
  public void first_function_with_simple_condition_and_sugar_around_number_in_condition()
      throws Exception {
    String rule =
        "The first item from addresses with zip_code equal to the number 12345 as a first address";
    String schema = "{addresses:[{zip_code: 1, city: Berlin}]}";

    End2AstRunner.run(
        rule,
        schema,
        r ->
            r.variables()
                .hasSizeOf(1)
                .first()
                .hasName("a first address")
                .operandFunction()
                .hasName("FIRST")
                .hasType(DataPropertyType.Object)
                .sizeOfParameters(1)
                .parameters()
                .first()
                .function()
                .hasName("WHERE")
                .sizeOfParameters(2)
                .parameters()
                .first()
                .property("addresses")
                .hasType(DataPropertyType.Array)
                .parentList()
                .second()
                .lambdaCondition()
                .hasOperator(ASTComparisonOperator.EQUALS)
                .hasNoConnector()
                .leftProperty("zip_code")
                .hasType(DataPropertyType.Decimal)
                .parentCondition()
                .rightNumber(12345.0));
  }

  @Test
  public void first_function_with_simple_condition_with_explicit_array_path() throws Exception {
    String rule = "a first item from info.addresses with zip_code equals 12345 as a first address";
    String schema = "{info: {addresses:[{zip_code: 1, city: Berlin}]}}";

    End2AstRunner.run(
        rule,
        schema,
        r ->
            r.variables()
                .hasSizeOf(1)
                .first()
                .hasName("a first address")
                .operandFunction()
                .hasName("FIRST")
                .hasType(DataPropertyType.Object)
                .sizeOfParameters(1)
                .parameters()
                .first()
                .function()
                .hasName("WHERE")
                .sizeOfParameters(2)
                .parameters()
                .first()
                .property("addresses")
                .hasType(DataPropertyType.Array)
                .parentList()
                .second()
                .lambdaCondition()
                .hasOperator(ASTComparisonOperator.EQUALS)
                .hasNoConnector()
                .leftProperty("zip_code")
                .hasType(DataPropertyType.Decimal)
                .parentCondition()
                .rightNumber(12345.0));
  }

  //  @Test
  //  public void take_function_simple() throws Exception {
  //    String rule = "take 10 items from addresses as a first address";
  //    String schema = "{addresses:[{zip_code: 1, city: Berlin}]}";
  //
  //    End2AstRunner.run(
  //        rule,
  //        schema,
  //        r ->
  //            r.variables()
  //                .hasSizeOf(1)
  //                .first()
  //                  .hasName("a first address")
  //                  .operandFunction()
  //                    .hasName("TAKE")
  //                    .hasType(DataPropertyType.Array)
  //                    .sizeOfParameters(1)
  //                    .parameters()
  //                      .first()
  //
  //    );
  //  }
}
