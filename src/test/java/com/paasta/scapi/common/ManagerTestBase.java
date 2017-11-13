package com.paasta.scapi.common;


import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import sonia.scm.Manager;
import sonia.scm.ModelObject;
import sonia.scm.SCMContextProvider;

/**
 *
 * @author Sebastian Sdorra
 *
 * @param <T>
 * @param <E>
 */
public abstract class ManagerTestBase<T extends ModelObject, E extends Exception>
{

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    protected SCMContextProvider contextProvider;

    protected Manager<T, E> manager;

    @Before
    public void setUp() throws IOException {
        contextProvider = MockUtil.getSCMContextProvider(tempFolder.newFolder());
        manager = createManager();
        manager.init(contextProvider);
    }

    @After
    public void tearDown() throws IOException {
        manager.close();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    protected abstract Manager<T, E> createManager();

}