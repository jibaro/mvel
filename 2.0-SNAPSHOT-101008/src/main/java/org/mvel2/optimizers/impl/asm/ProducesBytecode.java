package org.mvel2.optimizers.impl.asm;

import org.mvel2.asm.MethodVisitor;
import org.mvel2.integration.VariableResolverFactory;

/**
 * A {@link org.mvel2.integration.PropertyHandler} that implements this class advertises the to the
 * {@link ASMAccessorOptimizer} that it is able to generate bytecode for it's custom resolvers.<br/>
 * <br/>
 * The two methods defined by this interface (one for get, and one for set accessors) are passed an
 * ASM MethodVistor object.  It is to be assumed by the implementor that the JIT has already produced
 * the necessary bytecode up until this point.  The implementor most only implement the necessary bytecode
 * instructions to retrieve the property, leaving the resultant value on the stack.  <b>DO NOT</b> implement
 * bytecode that calls a return instruction such as ARETURN.  This will be done automatically by the JIT.<br/>
 * <br/>
 * See the following example:<br/>
 * <pre><code>
 *   public void produceBytecodeGet(MethodVisitor mv, String propertyName, VariableResolverFactory variableResolverFactory) {
 * mv.visitTypeInsn(CHECKCAST, "org/mvel/tests/main/res/SampleBean");
 * mv.visitLdcInsn(propertyName);
 * mv.visitMethodInsn(INVOKEVIRTUAL, "org/mvel/tests/main/res/SampleBean", "getProperty", "(Ljava/lang/String;)Ljava/lang/Object;");
 * }
 * </code></pre><br/>
 * This example (correctly) presumes that the property of interest exists on the stack, and simply performs the necessary
 * CHECKCAST, and call to the needed method in the accessor to translate the property call. <br/>
 * <br/>
 * This is taken from a working example which you can examine in the MVEL unit tests.
 * The class is: org.mvel.tests.main.res.SampleBeanAccessor
 */
public interface ProducesBytecode {
    public void produceBytecodeGet(MethodVisitor mv, String propertyName, VariableResolverFactory factory);

    public void produceBytecodePut(MethodVisitor mv, String propertyName, VariableResolverFactory factory);
}
